package com.example.managesolution.controller;

import com.example.managesolution.data.dto.MemberShipCareDTO;
import com.example.managesolution.data.dto.PtCareDTO;
import com.example.managesolution.service.MembershipService;
import com.example.managesolution.service.PtPackageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Comparator;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/care")
public class CareController {

    private final PtPackageService ptPackageService;
    private final MembershipService membershipService;

    @GetMapping("")
    public String careDashboard(@RequestParam(required = false) String keyword, Model model) {
        List<PtCareDTO> ptMembers;
        List<MemberShipCareDTO> membershipMembers;
        if (keyword != null && !keyword.isBlank()) {
            // ✅ 모든 PT 패키지 회원 조회 (첫 수업 여부 조건 없음)
            ptMembers = ptPackageService.findByNamePhone(keyword);

            // ✅ 모든 회원권 회원 조회
            membershipMembers = membershipService.findByNamePhone(keyword);
        } else {
            // ✅ 모든 PT 패키지 회원 조회 (첫 수업 여부 조건 없음)
            ptMembers = ptPackageService.getAllCareMembers();

            // ✅ 모든 회원권 회원 조회
            membershipMembers = membershipService.getAllCareMembers();
        }


        model.addAttribute("ptMembers", ptMembers);
        model.addAttribute("membershipMembers", membershipMembers);
        model.addAttribute("keyword", keyword);

        return "member/care";
    }


}
