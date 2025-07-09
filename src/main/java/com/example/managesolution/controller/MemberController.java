package com.example.managesolution.controller;

import com.example.managesolution.data.domain.Member;
import com.example.managesolution.data.domain.Membership;
import com.example.managesolution.data.domain.PtPackage;
import com.example.managesolution.data.dto.MemberFormDTO;
import com.example.managesolution.service.*;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/members")
public class MemberController {
    private final MemberService memberService;
    private final ProductService productService;
    private final MembershipService membershipService;
    private final PtPackageService ptPackageService;
    private final TrainerService trainerService;


    //회원 목록 조회
    @GetMapping("")
    public String list(@RequestParam(defaultValue = "1") int page,
                       @RequestParam(required = false) String keyword,
                       @RequestParam(required = false) String status,
                       Model model) {

        int pageSize = 10;
        List<Member> members;

        // 상태와 키워드 둘 다 존재할 경우
        if (status != null && !status.isBlank() && keyword != null && !keyword.isBlank()) {
            members = memberService.findByStatusAndKeyword(status, keyword, page, pageSize);
        }
        // 상태만 존재할 경우
        else if (status != null && !status.isBlank()) {
            members = memberService.findByStatus(status, page, pageSize);
        }
        // 키워드만 존재할 경우
        else if (keyword != null && !keyword.isBlank()) {
            members = memberService.findByNameContaining(keyword, page, pageSize);
        }
        // 둘 다 없는 경우
        else {
            members = memberService.findPaged(page, pageSize);
        }

        int totalCount = memberService.countAll();
        int totalPages = (int) Math.ceil((double) totalCount / pageSize);

        model.addAttribute("members", members);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("keyword", keyword);
        model.addAttribute("status", status);

        return "member/list";
    }


    //회원 + 상품 등록 폼
    @GetMapping("/new")
    public String form(Model model) {
        MemberFormDTO memberFormDTO = new MemberFormDTO();
        model.addAttribute("memberForm", memberFormDTO);
        model.addAttribute("mode", "create");

        System.out.println("등록 진입: memberId = " + memberFormDTO.getMemberId());

        model.addAttribute("membershipProducts", productService.getMembershipProducts());
        model.addAttribute("ptProducts", productService.getPtProducts());
        model.addAttribute("trainers", trainerService.getTrainerList());

        return "member/form";
    }


    //새로운 회원 및 상품 저장
    @PostMapping
    public String save(@ModelAttribute @Valid MemberFormDTO dto, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            // 상품/트레이너 목록 재주입 필요
            model.addAttribute("membershipProducts", productService.getMembershipProducts());

            return "member/form";
        }

        memberService.save(dto);
        return "redirect:/members";
    }


    //회원 수정 + 상품 까지 수정
    @GetMapping("/{id}/edit")
    public String edit(@PathVariable Long id, Model model) {
        Member member = memberService.findById(id);
        Membership membership = membershipService.findByMemberId(id);
        PtPackage ptPackage = ptPackageService.findByMemberId(id);

        MemberFormDTO dto = new MemberFormDTO();
        dto.setMemberId(member.getMemberId());
        dto.setName(member.getName());
        dto.setPhone(member.getPhone());
        dto.setBirthDate(member.getBirthDate());
        dto.setGender(member.getGender());
        dto.setMemo(member.getMemo());

        if (membership != null) {
            dto.setProductType("MEMBERSHIP");
            dto.setMembershipProductId(membership.getProductId());
            dto.setMembershipStartDate(membership.getStartDate());
            dto.setMembershipEndDate(membership.getEndDate());
            dto.setMembershipPrice(membership.getPrice());
        } else if (ptPackage != null) {
            dto.setProductType("PT");
            dto.setPtProductId(ptPackage.getProductId());
            dto.setTrainerId(ptPackage.getTrainerId());
            dto.setPtStartDate(ptPackage.getStartDate());
            dto.setPtEndDate(ptPackage.getEndDate());
            dto.setPtTotalCount(ptPackage.getTotalCount());
            dto.setPtPrice(ptPackage.getPrice());
        }

        model.addAttribute("memberForm", dto);

        model.addAttribute("mode", "edit");
        model.addAttribute("membershipProducts", productService.getMembershipProducts());
        model.addAttribute("ptProducts", productService.getPtProducts());
        model.addAttribute("trainers", trainerService.getTrainerList());

        return "member/form";
    }


    // 회원 수정 및 상품 수정
    @PostMapping("/{id}/edit")
    public String update(@PathVariable Long id, @Valid @ModelAttribute("dto")  MemberFormDTO dto,
                         BindingResult bindingResult, Model model) {

        if (bindingResult.hasErrors()) {
            // 다시 등록 폼으로
            return "member/form";
        }
        Member member = memberService.findById(id);
        dto.setStatus(member.getStatus());
        memberService.update(id, dto);
        return "redirect:/members";
    }

    //회원 삭제 상품은 cascade 삭제
    @PostMapping("/{id}/delete")
    public String delete(@PathVariable Long id) {
        memberService.delete(id);
        return "redirect:/members";
    }

    //기존 member에서 상품만 등록 하려는 폼 보여주기
    @GetMapping("/{id}/register")
    public String registerForm(@PathVariable Long id, Model model) {
        Member member = memberService.findById(id);

        MemberFormDTO dto = new MemberFormDTO();
        dto.setMemberId(id);
        dto.setName(member.getName());
        dto.setPhone(member.getPhone());
        dto.setBirthDate(member.getBirthDate());
        dto.setGender(member.getGender());
        dto.setMemo(member.getMemo());
        dto.setStatus(member.getStatus());


        model.addAttribute("memberForm", dto);

        model.addAttribute("mode", "register");
        model.addAttribute("membershipProducts", productService.getMembershipProducts());
        model.addAttribute("ptProducts", productService.getPtProducts());
        model.addAttribute("trainers", trainerService.getTrainerList());

        return "member/productRegister";
    }

    // 상품만 등록(결제관리 마감회원에서 넘어온것임)
    @PostMapping("/{id}/register")
    public String registerProduct(@PathVariable Long id, @Valid @ModelAttribute("memberForm") MemberFormDTO dto,
                                  BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("membershipProducts", productService.getMembershipProducts());
            model.addAttribute("ptProducts", productService.getPtProducts());
            model.addAttribute("trainers", trainerService.getTrainerList());
            return "payment/list";
        }
        memberService.registerNewProduct(id, dto);
        return "redirect:/payment";
    }



}
