package com.example.managesolution.controller;

import com.example.managesolution.data.dto.trainer.request.TrainerFormDTO;
import com.example.managesolution.service.PtPackageService;
import com.example.managesolution.service.TrainerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@Controller
@RequestMapping("/trainer")
@RequiredArgsConstructor
public class TrainerController {

    private final PtPackageService ptPackageService;
    private final PasswordEncoder passwordEncoder;
    private final TrainerService trainerService;

    // 강사 관리 페이지 조회
    @GetMapping("")
    public String trainerPage(Model model) {

        model.addAttribute("trainers", trainerService.getTrainerList());
        return "trainer/trainer"; // 오른쪽 테이블 비어있음
    }

    //강사별 회원 조회
    @GetMapping("/{id}")
    public String trainerDetail(@PathVariable Long id, Model model) {
        model.addAttribute("trainers", trainerService.getTrainerList());
        model.addAttribute("ptMembers", ptPackageService.findByTrainerId(id));
        model.addAttribute("selectedTrainerId", id);
        return "trainer/trainer";
    }

    // 강사 등록 페이지 조회
    @GetMapping("/register")
    public String showRegisterForm(Model model) {
        model.addAttribute("trainer", new TrainerFormDTO());
        return "trainer/trainer-form";
    }

    // 강사 저장
    @PostMapping("/register")
    public String save(@ModelAttribute("trainer") @Valid TrainerFormDTO trainerFormDTO, BindingResult bindingResult, Model model) {
        if (!trainerFormDTO.getPassword().equals(trainerFormDTO.getPasswordConfirm())) {
            bindingResult.rejectValue("passwordConfirm", "error.passwordConfirm", "비밀번호가 일치하지 않습니다.");
            return "trainer/trainer-form";
        }

        if (bindingResult.hasErrors()) {
            model.addAttribute("trainer", trainerFormDTO);
            return "trainer/trainer-form";
        }

        trainerFormDTO.setRole("TRAINER");
        trainerFormDTO.setCreatedAt(LocalDateTime.now());
        trainerFormDTO.setPassword(passwordEncoder.encode(trainerFormDTO.getPassword()));

        trainerService.save(trainerFormDTO);
        return "redirect:/trainer";
    }

    // 트레이너 변경
    @PostMapping("/change")
    public String changeTrainer(@RequestParam List<Long> selectedMemberIds,
                                @RequestParam Long newTrainerId) {
        ptPackageService.updateTrainerForMembers(selectedMemberIds, newTrainerId);
        return "redirect:/trainer";
    }



}
