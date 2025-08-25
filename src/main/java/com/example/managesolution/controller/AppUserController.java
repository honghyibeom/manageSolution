package com.example.managesolution.controller;

import com.example.managesolution.data.dto.dashboard.response.DashboardTimeLineDTO;
import com.example.managesolution.data.dto.dashboard.response.MemberStatsDTO;
import com.example.managesolution.data.dto.dashboard.response.ProductRankingDTO;
import com.example.managesolution.data.dto.dashboard.response.TrainerPtStatsDTO;
import com.example.managesolution.service.DashboardService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class AppUserController {
    private final DashboardService dashboardService;

    //로그인
    @GetMapping("/login")
    public String login() {
        return "login";
    }

    //대시보드 조회
    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        // ✅ 회원 통계
        MemberStatsDTO memberStats = dashboardService.getMemberStats();
        model.addAttribute("memberStats", memberStats);

        DashboardTimeLineDTO timeline = dashboardService.getTodayTimeline();
        model.addAttribute("todaySessions", timeline.getTodaySessions());
        model.addAttribute("todaySessionsByTrainer", timeline.getTodaySessionsByTrainer());
        model.addAttribute("maxSessionCount", timeline.getMaxSessionCount());
        model.addAttribute("timeLabels", timeline.getTimeLabels());


        // ✅ 상품 순위
        List<ProductRankingDTO> productRankings = dashboardService.getProductRankings();
        model.addAttribute("productRankings", productRankings);

        // ✅ 트레이너별 PT 회원 월별 통계
        TrainerPtStatsDTO trainerPtStats = dashboardService.getTrainerPtStats();
        model.addAttribute("trainerPtStats", trainerPtStats);


        return "/dashboard";
    }

}
