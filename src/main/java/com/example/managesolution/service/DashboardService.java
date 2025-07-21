package com.example.managesolution.service;

import com.example.managesolution.data.dto.dashboard.response.*;
import com.example.managesolution.mapper.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.time.YearMonth;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
@RequiredArgsConstructor
public class DashboardService {
    private final MemberMapper memberMapper;
    private final PtSessionMapper ptSessionMapper;
    private final ProductMapper productMapper;
    private final TrainerMapper trainerMapper;

    //회원 통계
    public MemberStatsDTO getMemberStats() {
        return memberMapper.getMemberStats();
    }

//    오늘 PT 일정
public DashboardTimeLineDTO getTodayTimeline() {
    List<DashboardSessionDTO> todaySessions = ptSessionMapper.findTodaySessions();

    int timelineStartMinutes = 8 * 60;   // 8AM
    int timelineEndMinutes = 24 * 60;    // 12AM
    int timelineTotalMinutes = timelineEndMinutes - timelineStartMinutes; // 16시간 * 60분 = 960

    // left, width 계산
    todaySessions.forEach(dto -> {
        LocalTime time = dto.getSessionTime();
        int startMinutes = time.getHour() * 60 + time.getMinute();
        int durationMinutes = 60; // 세션 길이 (1시간 기준)

        double left = ((startMinutes - timelineStartMinutes) / (double) timelineTotalMinutes) * 100.0;
        double width = (durationMinutes / (double) timelineTotalMinutes) * 100.0;

        dto.setLeft(left);
        dto.setWidth(width);
    });

    // 트레이너별 그룹핑 + overlapIndex 계산
    Map<String, List<DashboardSessionDTO>> todaySessionsByTrainer =
            todaySessions.stream()
                    .collect(Collectors.groupingBy(DashboardSessionDTO::getTrainerName));

    todaySessionsByTrainer.forEach((trainer, list) -> {
        Map<LocalTime, Integer> timeCountMap = new HashMap<>();
        for (DashboardSessionDTO dto : list) {
            int idx = timeCountMap.getOrDefault(dto.getSessionTime(), 0);
            dto.setOverlapIndex(idx);
            timeCountMap.put(dto.getSessionTime(), idx + 1);
        }
    });

    int maxSessionCount = todaySessionsByTrainer.values()
            .stream()
            .mapToInt(List::size)
            .max()
            .orElse(0);

    List<String> timeLabels = IntStream.rangeClosed(8, 24)
            .mapToObj(h -> {
                int displayHour = h > 12 ? h - 12 : h;
                String ampm = h < 12 ? "AM" : "PM";
                return displayHour + " " + ampm;
            })
            .toList();

    return DashboardTimeLineDTO.builder()
            .todaySessions(todaySessions)
            .todaySessionsByTrainer(todaySessionsByTrainer)
            .maxSessionCount(maxSessionCount)
            .timeLabels(timeLabels)
            .build();
}


    // 상품 순위
    public List<ProductRankingDTO> getProductRankings() {
        return productMapper.findProductRankings();
    }

    // 트레이너별 회원통계(6개월)
    public TrainerPtStatsDTO getTrainerPtStats() {
        List<String> months = getLast6Months();

        TrainerPtStatsDTO stats = new TrainerPtStatsDTO();
        stats.setLabels(months);

        //트레이너 이름 다 가져오기
        List<String> trainerNames = trainerMapper.findAllTrainerNames();

        List<TrainerDatasetDTO> datasets = new ArrayList<>();
        // 이름별 달별로 갯수 가져오기
        for (String trainerName : trainerNames) {
            List<MonthCountDTO> rawCounts = trainerMapper.getMonthlyMemberCountsRaw(trainerName);

            Map<String, Integer> countMap = rawCounts.stream()
                    .collect(Collectors.toMap(MonthCountDTO::getMonth, MonthCountDTO::getCount));

            List<Integer> data = months.stream()
                    .map(m -> countMap.getOrDefault(m, 0))
                    .toList();

            TrainerDatasetDTO dataset = new TrainerDatasetDTO();
            dataset.setLabel(trainerName);
            dataset.setData(data);
            dataset.setBackgroundColor(getRandomColorForTrainer(trainerName));

            datasets.add(dataset);
            System.out.println(dataset.getData());
            System.out.println(dataset.getLabel());
        }
        stats.setDatasets(datasets);
        return stats;
    }

    // 6개월 구하기
        private List<String> getLast6Months() {
            List<String> months = new ArrayList<>();
            YearMonth current = YearMonth.now();

            for (int i = 5; i >= 0; i--) {
                YearMonth ym = current.minusMonths(i);
                months.add(ym.toString()); // yyyy-MM
            }

            return months;
        }

    private String getRandomColorForTrainer(String trainerName) {
        // trainerName을 기반으로 시드값 생성
        int seed = trainerName.hashCode();
        Random random = new Random(seed);

        int r = random.nextInt(156) + 100; // 100~255
        int g = random.nextInt(156) + 100;
        int b = random.nextInt(156) + 100;

        double alpha = 0.8;

        return String.format("rgba(%d, %d, %d, %.1f)", r, g, b, alpha);
    }


}
