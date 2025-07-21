package com.example.managesolution.controller;

import com.example.managesolution.data.domain.Member;
import com.example.managesolution.data.dto.member.request.MemberFormDTO;
import com.example.managesolution.data.dto.member.response.MemberProductDTO;
import com.example.managesolution.service.*;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/members")
public class MemberController {
    private final MemberService memberService;
    private final ProductService productService;
    private final TrainerService trainerService;


    //회원 목록 조회
    @GetMapping("")
    public String list(@RequestParam(defaultValue = "1") int page,
                       @RequestParam(required = false) String keyword,
                       @RequestParam(required = false) String status,
                       Model model) {

        int pageSize = 10;
        List<MemberProductDTO> members = memberService.findMembers(status, keyword, page, pageSize);

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
    public String save(@ModelAttribute("memberForm") @Valid MemberFormDTO dto, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            // 상품/트레이너 목록 재주입 필요
            model.addAttribute("membershipProducts", productService.getMembershipProducts());
            model.addAttribute("ptProducts", productService.getPtProducts());
            model.addAttribute("trainers", trainerService.getTrainerList());
            model.addAttribute("memberForm", dto);

            return "member/form";
        }

        memberService.save(dto);
        return "redirect:/members";
    }


    //회원 수정 + 상품 까지 수정
    @GetMapping("/{id}/edit")
    public String edit(@PathVariable Long id, Model model) {

        MemberFormDTO dto = memberService.toFormDTO(id);

        model.addAttribute("memberForm", dto);
        model.addAttribute("mode", "edit");

        model.addAttribute("membershipProducts", productService.getMembershipProducts());
        model.addAttribute("ptProducts", productService.getPtProducts());
        model.addAttribute("trainers", trainerService.getTrainerList());

        return "member/form";
    }


    // 회원 수정 및 상품 수정
    @PostMapping("/{id}/edit")
    public String update(@PathVariable Long id, @Valid @ModelAttribute(("memberForm"))  MemberFormDTO dto,
                         BindingResult bindingResult, Model model) {

        if (bindingResult.hasErrors()) {
            model.addAttribute("membershipProducts", productService.getMembershipProducts());
            model.addAttribute("ptProducts", productService.getPtProducts());
            model.addAttribute("trainers", trainerService.getTrainerList());
            model.addAttribute("memberForm", dto);
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
    @GetMapping("/{id}/register/product")
    public String registerForm(@PathVariable Long id, Model model) {
        MemberFormDTO dto = memberService.toBasicFormDTO(id);

        model.addAttribute("memberForm", dto);

        model.addAttribute("mode", "register");
        model.addAttribute("membershipProducts", productService.getMembershipProducts());
        model.addAttribute("ptProducts", productService.getPtProducts());
        model.addAttribute("trainers", trainerService.getTrainerList());

        return "member/productRegister";
    }

    // 상품만 등록
    @PostMapping("/{id}/register")
    public String registerProduct(@PathVariable Long id, @Valid @ModelAttribute("memberForm") MemberFormDTO dto,
                                  BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("membershipProducts", productService.getMembershipProducts());
            model.addAttribute("ptProducts", productService.getPtProducts());
            model.addAttribute("trainers", trainerService.getTrainerList());
            model.addAttribute("memberForm", dto);
            return "member/form";
        }
        memberService.registerNewProduct(id, dto);
        return "redirect:/payment";
    }



}
