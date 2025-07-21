package com.example.managesolution.service;

import com.example.managesolution.data.domain.PtSession;
import com.example.managesolution.data.dto.ptSession.response.DayLessonDTO;
import com.example.managesolution.data.dto.ptSession.response.LessonDTO;
import com.example.managesolution.data.dto.ptSession.request.PtSessionDTO;
import com.example.managesolution.mapper.PtSessionMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PtSessionService {
    private final PtSessionMapper ptSessionMapper;


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

}
