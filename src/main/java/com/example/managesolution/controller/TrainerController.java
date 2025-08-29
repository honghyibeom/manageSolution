package com.example.managesolution.controller;

import com.example.managesolution.data.dto.trainer.request.TrainerFormDTO;
import com.example.managesolution.data.enumerate.Role;
import com.example.managesolution.service.SubscriptionService;
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

    private final SubscriptionService subscriptionService;
    private final PasswordEncoder passwordEncoder;
    private final TrainerService trainerService;

    // 트레이너 관리 페이지 조회
    @GetMapping("")
    public String trainerPage(Model model) {

        model.addAttribute("trainers", trainerService.getTrainerList());
        return "trainer/trainer"; // 오른쪽 테이블 비어있음
    }

    //트레이너별 회원 조회
    @GetMapping("/{id}")
    public String trainerDetail(@PathVariable Long id, Model model) {
        model.addAttribute("trainers", trainerService.getTrainerList());
        model.addAttribute("ptMembers", subscriptionService.findByTrainerId(id));
        model.addAttribute("selectedTrainerId", id);
        return "trainer/trainer";
    }

    // 트레이너 등록 페이지 조회
    @GetMapping("/register")
    public String showRegisterForm(Model model) {
        model.addAttribute("trainer", new TrainerFormDTO());
        return "trainer/trainer-form";
    }

    // 트레이너 저장
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

        trainerFormDTO.setRole(Role.TRAINER);
        trainerFormDTO.setCreatedAt(LocalDateTime.now());
        trainerFormDTO.setPassword(passwordEncoder.encode(trainerFormDTO.getPassword()));

        trainerService.save(trainerFormDTO);
        return "redirect:/trainer";
    }

    // 트레이너 변경
    @PostMapping("/change")
    public String changeTrainer(@RequestParam List<Long> selectedMemberIds,
                                @RequestParam Long newTrainerId) {
        subscriptionService.updateTrainerForMembers(selectedMemberIds, newTrainerId);
        return "redirect:/trainer";
    }

    // 트레이너 정보 수정
    @GetMapping("/{id}/edit")
    public String edit(@PathVariable Long id, Model model) {
        TrainerFormDTO trainerFormDTO = trainerService.getFormDTO(id);
        model.addAttribute("trainer", trainerFormDTO);

        return "trainer/trainer-form";
    }

    // 트레이너 정보 수정
    @PostMapping("/{id}/edit")
    public String editTrainer(@ModelAttribute("trainer") @Valid TrainerFormDTO trainerFormDTO, BindingResult bindingResult , Model model, @PathVariable Long id) {
        trainerFormDTO.setTrainerId(id);
        if (!trainerFormDTO.getPassword().equals(trainerFormDTO.getPasswordConfirm())) {
            bindingResult.rejectValue("passwordConfirm", "error.passwordConfirm", "비밀번호가 일치하지 않습니다.");
            model.addAttribute("trainer", trainerFormDTO);
            return "trainer/trainer-form";
        }

        if (bindingResult.hasErrors()) {
            model.addAttribute("trainer", trainerFormDTO);
            return "trainer/trainer-form";
        }

        trainerFormDTO.setPassword(passwordEncoder.encode(trainerFormDTO.getPassword()));
        trainerService.update(id, trainerFormDTO);
        return "redirect:/trainer";
    }

    @PostMapping("{id}/delete")
    public String deleteTrainer(@PathVariable Long id, Model model) {
        trainerService.delete(id);
        return "redirect:/trainer";
    }



}
