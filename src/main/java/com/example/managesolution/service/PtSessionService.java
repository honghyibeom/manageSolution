package com.example.managesolution.service;

import com.example.managesolution.data.domain.PtSession;
import com.example.managesolution.data.domain.Subscription;
import com.example.managesolution.data.dto.ResponseDTO;
import com.example.managesolution.data.dto.ptSession.request.RegisterRangeFormDTO;
import com.example.managesolution.data.dto.ptSession.request.SessionUpdateDTO;
import com.example.managesolution.data.dto.ptSession.response.*;
import com.example.managesolution.data.enumerate.Status;
import com.example.managesolution.exception.CustomException;
import com.example.managesolution.exception.ErrorCode;
import com.example.managesolution.mapper.PtSessionMapper;
import com.example.managesolution.mapper.SubscriptionMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Log4j2
public class PtSessionService {
    private final PtSessionMapper ptSessionMapper;
    private final SubscriptionMapper subscriptionMapper;


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
        if (form.getTrainerId() == null || form.getMemberId() == null || form.getSubscriptionId() == null) {
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
        String batchId = UUID.randomUUID().toString();
        for (LocalDate date : dates) {

            //이거 특정한 회원이 동일한 날짜/시간에 이미 세션이 있는지 확인하게 변경해야됨.
            if (ptSessionMapper.existsMemberExact(form.getMemberId() ,date, form.getTime()) == 1) {
                throw new CustomException(ErrorCode.EXIST_SESSION);
            }
            PtSession ptSession = PtSession.builder()
                    .trainerId(form.getTrainerId())
                    .memberId(form.getMemberId())
                    .subscriptionId(form.getSubscriptionId())
                    .sessionDate(date)
                    .sessionTime(form.getTime())
                    .batchId(batchId)
                    .build();
            ptSessionMapper.insert(ptSession);
            createdCount++;
        }

        Subscription subscription = subscriptionMapper.findBySubscriptionId(form.getSubscriptionId());
        int count = subscription.getRemainingCount() - createdCount;
        if (count < 0) {
            count = 0;
        }

        subscription.setRemainingCount(count);
        subscriptionMapper.updateSubscription(subscription);

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

    @Transactional
    public ResponseDTO deleteGroupSession(String batchId) {
        List<PtSession> session = ptSessionMapper.findSessionIdByBatchId(batchId);
        for (PtSession sess : session) {
            subscriptionMapper.increaseRemainingCount(sess.getSubscriptionId());
        }
        ptSessionMapper.deleteByBatchId(batchId);
        return ResponseDTO.builder()
                .message("그룹 삭제 완료")
                .build();
    }
    public ResponseDTO getGroupSession(String batchId) {
        List<SessionGroupDTO> group = ptSessionMapper.findSessionViewByBatchId(batchId);
        return ResponseDTO.builder()
                .message("group 조회")
                .body(group)
                .build();
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
                new SessionSummaryDTO("총 건수", total, false),
                new SessionSummaryDTO("출석률", attendRate, true),
                new SessionSummaryDTO("노쇼", noShowCount, false),
                new SessionSummaryDTO("임박(30분↓)", imminentCount, false)
        );
    }

    public List<DateGroupDTO> getDateGroup(List<SessionViewDTO> sessions) {

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

    /** start~end를 1일씩 순회하며 repeatDays(1=월..7=일)에 속하는 날짜만 반환 */
    private List<LocalDate> expandDates(LocalDate start, LocalDate end, List<Integer> repeatDays) {
        List<LocalDate> out = new ArrayList<>();
        for (LocalDate d = start; !d.isAfter(end); d = d.plusDays(1)) {
            int dow = d.getDayOfWeek().getValue(); // 1=월..7=일
            if (repeatDays.contains(dow)) out.add(d);
        }
        return out;
    }

}
