package com.example.managesolution.controller;

import com.example.managesolution.data.domain.AppUser;
import com.example.managesolution.data.domain.PtSession;
import com.example.managesolution.data.dto.*;
import com.example.managesolution.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequiredArgsConstructor
@RequestMapping("/lecture")
public class PtSessionController {
    private final PtSessionService ptSessionService;
    private final AppUserService appUserService;
    private final PtPackageService ptPackageService;
    private final AttendanceService attendanceService;
    private final TrainerService trainerService;

    @GetMapping("")
    public String index(Model model) {
        model.addAttribute("year", LocalDate.now().getYear());
        model.addAttribute("month", LocalDate.now().getMonthValue());
        return "lecture/list";
    }


//    --------------------------------------json----------------------------------------------------------
    @ResponseBody
    @GetMapping("/month")
    public List<DayLessonDTO> getMonthLessons(@RequestParam int year, @RequestParam int month) {
        return ptSessionService.getLessonCalendar(year, month);
    }

    @ResponseBody
    @GetMapping("/day")
    public List<LessonDTO> getLessonsByDate(@RequestParam("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        return ptSessionService.getLessonsByDate(date);
    }

    @ResponseBody // JSON 반환을 위함
    @PostMapping("/register")
    public ResponseEntity<?> registerLecture(@ModelAttribute PtSessionDTO form) {
        ptSessionService.register(form);
        return ResponseEntity.ok(Map.of("success", true));
    }

    @ResponseBody
    @PostMapping("/delete")
    public Map<String, Object> deleteLessons(@RequestBody List<Long> sessionIds) {
        Map<String, Object> response = new HashMap<>();
        try {
            ptSessionService.deleteByIds(sessionIds);
            response.put("success", true);
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", e.getMessage());
        }
        return response;
    }

    @GetMapping("/trainers")
    @ResponseBody
    public List<TrainerDTO> getTrainerList() {
        return trainerService.getTrainerList(); // 트레이너 리스트 반환
    }

    @GetMapping("/members")
    @ResponseBody
    public List<PtMemberDTO> getMembersByTrainer(@RequestParam Long trainerId) {
        return ptPackageService.findByTrainerId(trainerId);
    }

    @GetMapping("/package/active")
    @ResponseBody
    public Map<String, Long> getActivePackage(@RequestParam Long memberId) {
        Long packageId = ptPackageService.findPackageIdByMemberId(memberId);
        return Map.of("packageId", packageId);
    }

    @PostMapping("/attend")
    @ResponseBody
    public ResponseEntity<?> attend(@RequestBody AttendanceRequestDTO dto) {
        try {
            attendanceService.attend(dto.getMemberId(), dto.getSessionId(), dto.getPackageId(), dto.getStatus());
            return ResponseEntity.ok().body(Collections.singletonMap("success", true));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Collections.singletonMap("error", e.getMessage()));
        }
    }

}
