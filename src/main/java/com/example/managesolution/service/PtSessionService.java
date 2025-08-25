package com.example.managesolution.service;

import com.example.managesolution.data.domain.PtPackage;
import com.example.managesolution.data.domain.PtSession;
import com.example.managesolution.data.dto.ResponseDTO;
import com.example.managesolution.data.dto.ptSession.request.RegisterRangeFormDTO;
import com.example.managesolution.data.dto.ptSession.request.SessionUpdateDTO;
import com.example.managesolution.data.dto.ptSession.response.*;
import com.example.managesolution.data.dto.ptSession.request.PtSessionDTO;
import com.example.managesolution.data.enumerate.Status;
import com.example.managesolution.exception.CustomException;
import com.example.managesolution.exception.ErrorCode;
import com.example.managesolution.mapper.PtPackageMapper;
import com.example.managesolution.mapper.PtSessionMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Log4j2
public class PtSessionService {
    private final PtSessionMapper ptSessionMapper;
    private final PtPackageMapper ptPackageMapper;


    public List<DayLessonDTO> getLessonCalendar(int year, int month, Long trainerId) {

        // 해당 월의 첫째 날을 생성
        LocalDate start = LocalDate.of(year, month, 1);

        // 다음 달의 첫째 날을 구함
        LocalDate end = start.plusMonths(1);

        // 🔷 트레이너 조건에 따라 Mapper 호출하여 DB에서 수업 건수를 조회
        List<DayLessonDTO> result;
        if (trainerId != null) {
            result = ptSessionMapper.getSessionCountByMonthAndTrainer(start, end, trainerId);
        } else {
            // 트레이너 지정이 없는 경우 : 전체 일정 조회
            result = ptSessionMapper.getSessionCountByMonth(start, end);
        }


        // 결과를 Day -> 수업 건수 형태의 Map으로 변환
        Map<Integer, Integer> dayCountMap = result.stream()
                .collect(Collectors.toMap(DayLessonDTO::getDay, DayLessonDTO::getLessonCount));

        //켈린더 폼
        List<DayLessonDTO> calendar = new ArrayList<>();
        // 월의 첫 번째 날의 요일(1=월, 7=일)을 구함
        int firstDayOfWeek = start.getDayOfWeek().getValue();

        // 달력 앞쪽 빈 칸(비어있는 셀) 추가
        for (int i = 1; i < firstDayOfWeek; i++) {
            calendar.add(new DayLessonDTO(0, 0, false));
        }

        // 해당 월의 실제 일 수만큼 반복
        int daysInMonth = start.lengthOfMonth();
        for (int i = 1; i <= daysInMonth; i++) {
            // day에 해당하는 수업건수 조회, 없으면 0
            int count = dayCountMap.getOrDefault(i, 0);
            // 달력에 하루치 데이터 추가
            calendar.add(new DayLessonDTO(i, count, true));
        }

        // 달력 마지막 주를 7의 배수로 맞추기 위해 뒷부분에 빈 칸을 추가
        while (calendar.size() % 7 != 0) {
            calendar.add(new DayLessonDTO(0, 0, false));
        }

        return calendar;
    }


    public List<LessonDTO> getLessonsByDate(LocalDate date, Long trainerId) {
        if (trainerId != null) {
            return ptSessionMapper.selectLessonsByDateAndTrainer(Date.valueOf(date), trainerId);
        } else {
            return ptSessionMapper.selectLessonsByDate(Date.valueOf(date));
        }
    }

    @Transactional
    public void register(PtSessionDTO form) {
        // 반복 등록일 경우 -> 주어진 폼은 repeatDays, repeatWeeks;
        if (form.isRepeat()) {
            List<LocalDate> result = new ArrayList<>();
            List<Integer> daysOfWeek = form.getRepeatDays();
            int totalTargetCount = daysOfWeek.size() * form.getRepeatWeeks();
            LocalDate current = LocalDate.parse(form.getSessionDate());

            //요일별 반복 횟수 카운터
            int[] dayCounts = new int[8]; //1~7

            while (result.size() < totalTargetCount) {
                int currentDay = current.getDayOfWeek().getValue(); // 1~7 sessionDate 날짜의 요일

                // 사용자가 선택한 요일 리스트 포함 하는가 and 이 요일이 반복 횟수보다 덜 등장했는가
                if (daysOfWeek.contains(currentDay) && dayCounts[currentDay] < form.getRepeatWeeks()) {
                    result.add(current); // 날짜 리스트에 현재 날짜 추가
                    dayCounts[currentDay]++; //이 요일이 몇 번 나왓는지 카운트
                }
                current = current.plusDays(1);
            }

            for (LocalDate day : result) {
                PtSession ptSession = PtSession.builder()
                        .trainerId(form.getTrainerId())
                        .memberId(form.getMemberId())
                        .packageId(form.getPackageId())
                        .sessionDate(day)
                        .sessionTime(LocalTime.parse(form.getSessionTime()))
                        .build();
                ptSessionMapper.insert(ptSession);
            }
        }
        else {
            // 단일 등록
            PtSession session = PtSession.builder()
                    .trainerId(form.getTrainerId())
                    .memberId(form.getMemberId())
                    .packageId(form.getPackageId())
                    .sessionDate(LocalDate.parse(form.getSessionDate()))
                    .sessionTime(LocalTime.parse(form.getSessionTime()))
                    .build();
            ptSessionMapper.insert(session);
        }
    }

    @Transactional
    public void deleteByIds(List<Long> sessionIds) {
        for (Long sessionId : sessionIds) {
            ptSessionMapper.deleteById(sessionId);
        }
    }

    @Transactional
    public void deleteByIds(Long sessionId) {
        ptSessionMapper.deleteById(sessionId);
    }

    public Map<String, List<String>> getBusyMap(Long trainerId, LocalDate start, LocalDate end) {
        DateTimeFormatter HHmm = DateTimeFormatter.ofPattern("HH:mm");
        List<BusySlotDTO> rows = ptSessionMapper.findBusySlots(trainerId, start, end);
        return rows.stream().collect(Collectors.groupingBy(
                r -> r.getSessionDate().toString(),
                LinkedHashMap::new,
                Collectors.mapping(r -> r.getSessionTime().format(HHmm), Collectors.toList())
        ));
    }

    @Transactional
    public ResponseDTO registerRangeResponse(RegisterRangeFormDTO form) {
        //일단 지나간 시간대 이다? startDate를 내일로 변경시켜
        LocalDateTime today = LocalDateTime.now();
        LocalDateTime startDateTime = LocalDateTime.of(form.getStartDate(),form.getTime());

        if(startDateTime.isBefore(today)) {
            form.setStartDate(form.getStartDate().plusDays(1));
        }

        // 교차 필드 검증
        if (form.getStartDate() == null || form.getEndDate() == null || form.getTime() == null) {
            throw new CustomException(ErrorCode.NOT_EXIST_FIELD);
        }
        if (form.getEndDate().isBefore(form.getStartDate())) {
            throw new CustomException(ErrorCode.END_DATE_BEFORE);
        }
        if (form.getTrainerId() == null || form.getMemberId() == null || form.getPackageId() == null) {
            throw new CustomException(ErrorCode.NOT_EXIST_FIELD);
        }

        // start ~ end 사이 repeatDays 에 포함 되어있을때 날짜 모음
        List<LocalDate> dates = expandDates(form.getStartDate(), form.getEndDate(), form.getRepeatDays());

        //validation
        if (dates.isEmpty()) {
            throw new CustomException(ErrorCode.NOT_EXIST_DATES);
        }

        int createdCount = 0;
        // insert
        for (LocalDate date : dates) {

            //이거 특정한 회원이 동일한 날짜/시간에 이미 세션이 있는지 확인하게 변경해야됨.
            if (ptSessionMapper.existsMemberExact(form.getMemberId() ,date, form.getTime()) == 1) {
                throw new CustomException(ErrorCode.EXIST_SESSION);
            }
            PtSession ptSession = PtSession.builder()
                    .trainerId(form.getTrainerId())
                    .memberId(form.getMemberId())
                    .packageId(form.getPackageId())
                    .sessionDate(date)
                    .sessionTime(form.getTime())
                    .build();
            ptSessionMapper.insert(ptSession);
            createdCount++;
        }
        //등록했을때 횟수 차감
        PtPackage ptPackage = ptPackageMapper.findByPackageId(form.getPackageId());
        int count = ptPackage.getRemainingCount() - createdCount;
        if (count < 0) {
            count = 0;
        }
        ptPackage.setRemainingCount(count);
        ptPackageMapper.updatePtPackage(ptPackage);


        return ResponseDTO.builder()
                .message("저장 성공")
                .body(null)
                .build();

    }

    //새로운 수업 관리 session 조회
    public List<SessionViewDTO> getSessionView(LectureSearchDTO lectureSearchDTO, int page, int pageSize) {
        int offset = (page - 1) * pageSize;
        return ptSessionMapper.ptSessionViews(lectureSearchDTO, pageSize, offset);
    }

    /** start~end를 1일씩 순회하며 repeatDays(1=월..7=일)에 속하는 날짜만 반환 */
    private List<LocalDate> expandDates(LocalDate start, LocalDate end, List<Integer> repeatDays) {
        List<LocalDate> out = new ArrayList<>();
        for (LocalDate d = start; !d.isAfter(end); d = d.plusDays(1)) {
            int dow = d.getDayOfWeek().getValue(); // 1=월..7=일
            if (repeatDays.contains(dow)) out.add(d);
        }
        return out;
    }

    public List<SessionSummaryDTO> getSummeryList(List<SessionViewDTO> sessions) {
        long total = sessions.size();
        long attendCount = sessions.stream()
                .filter(s -> "출석".equalsIgnoreCase(s.getLabel()))
                .count();
        long noShowCount = sessions.stream()
                .filter(s -> "결석".equalsIgnoreCase(s.getLabel()))
                .count();

        // 출석률 계산
        long attendRate = Math.round(total > 0 ? (attendCount * 100.0 / total) : 0.0);

        // 임박 세션 수
        long imminentCount = sessions.stream()
                .filter(SessionViewDTO::getImminent)
                .count();

        return List.of(
                new SessionSummaryDTO("오늘 총 건수", total, false),
                new SessionSummaryDTO("출석률", attendRate, true),
                new SessionSummaryDTO("노쇼", noShowCount, false),
                new SessionSummaryDTO("임박(30분↓)", imminentCount, false)
        );
    }

    public List<DateGroupDTO> getDateGroup(LectureSearchDTO cond, List<SessionViewDTO> sessions) {

        // 날짜별 그룹핑
        Map<LocalDate, List<SessionViewDTO>> grouped = sessions.stream()
                .collect(Collectors.groupingBy(SessionViewDTO::getSessionDate));

        return grouped.entrySet().stream()
                .sorted(Map.Entry.comparingByKey())
                .map(e -> {
                    List<SessionViewDTO> daySessions = e.getValue();
                    int total = daySessions.size();
                    int booked = (int) daySessions.stream().filter(s -> "예약".equals(s.getLabel())).count();
                    int attended = (int) daySessions.stream().filter(s -> "출석".equals(s.getLabel())).count();
                    int noShow = (int) daySessions.stream().filter(s -> "결석".equals(s.getLabel())).count();
                    double rate = total > 0 ? (attended * 100.0 / total) : 0.0;

                    return new DateGroupDTO(
                            e.getKey(),
                            total, booked, attended, noShow, rate, daySessions
                    );
                })
                .toList();
    }

    public LectureSearchDTO applyDefaults(LectureSearchDTO cond) {
        if (cond.getStatus() == null || cond.getStatus().isEmpty()) {
            cond.setStatus(Arrays.asList(Status.BOOKED.name(), Status.ATTENDED.name(), Status.NO_SHOW.name()));
        }
        if (cond.getStartDate() == null) {
            cond.setStartDate(LocalDate.now());
        }
        if (cond.getEndDate() == null) {
            cond.setEndDate(LocalDate.now().withDayOfMonth(LocalDate.now().lengthOfMonth()));
        }
        return cond;
    }

    public void updateSession(SessionUpdateDTO dto) {
        ptSessionMapper.updateSession(dto.getSessionId(), dto.getDate(), dto.getTime(), dto.getTrainerId());
    }

}
