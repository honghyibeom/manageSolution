package com.example.managesolution.controller;

import com.example.managesolution.data.dto.statistics.response.StatisticsResponseDTO;
import com.example.managesolution.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.time.LocalDate;

@Controller
@RequestMapping("/statistics")
@RequiredArgsConstructor
public class StatisticsController {

    private final PaymentService paymentService;

    // 매출 통계 조회
    @GetMapping("")
    public String statistics(Model model) {

        model.addAttribute("today", LocalDate.now().toString());

        model.addAttribute("sales", paymentService.getSales());

        return "statistics/list";
    }

//    ---------------------------------------restAPI-------------------------------------
    //기간별 데이터 조회
    @GetMapping("/api")
    @ResponseBody
    public StatisticsResponseDTO statisticsData(@RequestParam String start,
                                                @RequestParam String end,
                                                @RequestParam String type) {

        LocalDate startDate = LocalDate.parse(start);
        LocalDate endDate = LocalDate.parse(end);

        return paymentService.getStatisticsData(startDate, endDate, type);
    }

}