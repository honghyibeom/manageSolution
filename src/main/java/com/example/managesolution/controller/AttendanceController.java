package com.example.managesolution.controller;

import com.example.managesolution.service.AttendanceService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor()
@RequestMapping("/attendance")
public class AttendanceController {
    private final AttendanceService attendanceService;

    @GetMapping("/report")
    public String getAttendanceReport(
            @RequestParam(required = false, name = "startDate") String startDate,
            @RequestParam(required = false, name = "endDate") String endDate,
            @RequestParam(required = false) String keyword,
            Model model) {

        // 출석 리스트 (필터 적용)
        model.addAttribute("reportList", attendanceService.findReport(startDate, endDate, keyword));

        // 차트 (최근 7일)
        model.addAttribute("chartData", attendanceService.getChartData());

        // 휴면 회원
        model.addAttribute("dormantList", attendanceService.findDormantMembers());

        return "attendance/list";
    }
}
