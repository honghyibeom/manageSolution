package com.example.managesolution.service;

import com.example.managesolution.data.domain.PtSession;
import com.example.managesolution.data.dto.DayLessonDTO;
import com.example.managesolution.data.dto.LessonDTO;
import com.example.managesolution.data.dto.PtSessionDTO;
import com.example.managesolution.data.dto.TrainerFormDTO;
import com.example.managesolution.mapper.PtSessionMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PtSessionService {
    private final PtSessionMapper ptSessionMapper;


    public List<DayLessonDTO> getLessonCalendar(int year, int month) {
        LocalDate start = LocalDate.of(year, month, 1);
        LocalDate end = start.plusMonths(1);

        List<DayLessonDTO> result = ptSessionMapper.getSessionCountByMonth(start, end);

        Map<Integer, Integer> dayCountMap = result.stream()
                .collect(Collectors.toMap(DayLessonDTO::getDay, DayLessonDTO::getLessonCount));

        List<DayLessonDTO> calendar = new ArrayList<>();
        int firstDayOfWeek = start.getDayOfWeek().getValue();

        for (int i = 1; i < firstDayOfWeek; i++) {
            calendar.add(new DayLessonDTO(0, 0, false));
        }

        int daysInMonth = start.lengthOfMonth();
        for (int i = 1; i <= daysInMonth; i++) {
            int count = dayCountMap.getOrDefault(i, 0);
            calendar.add(new DayLessonDTO(i, count, true));
        }

        while (calendar.size() % 7 != 0) {
            calendar.add(new DayLessonDTO(0, 0, false));
        }

        return calendar;
    }

    public List<LessonDTO> getLessonsByDate(LocalDate date) {
        return ptSessionMapper.selectLessonsByDate(Date.valueOf(date));
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
