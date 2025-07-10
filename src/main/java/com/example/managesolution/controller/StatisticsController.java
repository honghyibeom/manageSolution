package com.example.managesolution.controller;

import com.example.managesolution.data.dto.SalesDTO;
import com.example.managesolution.data.dto.StatisticsResponseDTO;
import com.example.managesolution.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/statistics")
@RequiredArgsConstructor
public class StatisticsController {

    private final PaymentService paymentService;

    @GetMapping("")
    public String statistics(Model model) {

        model.addAttribute("today", LocalDate.now().toString());

        model.addAttribute("sales", paymentService.getSales());

        return "statistics/list";
    }

//    ---------------------------------------restAPI-------------------------------------
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