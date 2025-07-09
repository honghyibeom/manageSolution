package com.example.managesolution.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/statistics")
@RequiredArgsConstructor
public class StatisticsController {

    @GetMapping("")
    public String statistics(Model model) {

        model.addAttribute("today", LocalDate.now().toString());




        List<String> labels = List.of("1월", "2월", "3월", "4월", "5월");
        List<Integer> sales = List.of(120, 150, 180, 130, 200);

        model.addAttribute("labels", labels);
        model.addAttribute("sales", sales);

        return "statistics/list";
    }
}
