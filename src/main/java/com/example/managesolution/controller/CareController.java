package com.example.managesolution.controller;

import com.example.managesolution.data.dto.care.response.CareDashboardDTO;
import com.example.managesolution.service.CareService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
@RequestMapping("/care")
public class CareController {
    private final CareService careService;
    // 회원 care 페이지 조회
    @GetMapping("")
    public String careDashboard(@RequestParam(required = false) String keyword, Model model) {
        CareDashboardDTO dashboard = careService.getCareDashboard(keyword);

        model.addAttribute("ptMembers", dashboard.getPtMembers());
        model.addAttribute("imminentMembers", dashboard.getImminentMembers());
        model.addAttribute("keyword", keyword);

        return "member/care";
    }


}
