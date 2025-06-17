package com.example.managesolution.controller;

import com.example.managesolution.data.domain.Member;
import com.example.managesolution.service.MemberService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/members")
public class MemberController {
    private final MemberService memberService;

    @GetMapping("")
    public String list(@RequestParam(defaultValue = "1") int page,
                       Model model) {

        int pageSize = 10; // 한 페이지당 회원 수
        List<Member> members = memberService.findPaged(page, pageSize);
        int totalCount = memberService.countAll();
        int totalPages = (int) Math.ceil((double) totalCount / pageSize);

        model.addAttribute("members", members);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", totalPages);

        return "member/list";
    }
    @GetMapping("/new")
    public String form(Model model) {
        model.addAttribute("member", new Member());
        return "member/form";
    }

    @PostMapping
    public String save(@Valid @ModelAttribute("member") Member member,
                       BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            // 다시 등록 폼으로
            return "member/form";
        }
        memberService.save(member);
        return "redirect:/members";
    }

    @GetMapping("/{id}/edit")
    public String edit(@PathVariable Long id, Model model) {
        model.addAttribute("member", memberService.findById(id));
        return "member/form";
    }

    @PostMapping("/{id}/edit")
    public String update(@PathVariable Long id, @Valid @ModelAttribute("member") Member member,
                         BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            // 다시 등록 폼으로
            return "member/form";
        }
        member.setMemberId(id);
        memberService.update(member);
        return "redirect:/members";
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable Long id) {
        memberService.delete(id);
        return "redirect:/members";
    }


}
