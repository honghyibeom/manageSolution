package com.example.managesolution.controller;

import com.example.managesolution.data.dto.ptSession.request.AttendanceRequestDTO;
import com.example.managesolution.data.dto.ptSession.request.PtSessionDTO;
import com.example.managesolution.data.dto.ptSession.response.DayLessonDTO;
import com.example.managesolution.data.dto.ptSession.response.LessonDTO;
import com.example.managesolution.data.dto.ptSession.response.PtMemberDTO;
import com.example.managesolution.data.dto.trainer.response.TrainerDTO;
import com.example.managesolution.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequiredArgsConstructor
@RequestMapping("/lecture")
public class PtSessionController {
    private final PtSessionService ptSessionService;
    private final PtPackageService ptPackageService;
    private final AttendanceService attendanceService;
    private final TrainerService trainerService;

    // 수업 관리 페이지 조회
    @GetMapping("")
    public String index(Model model) {
        model.addAttribute("year", LocalDate.now().getYear());
        model.addAttribute("month", LocalDate.now().getMonthValue());
        return "lecture/list";
    }


//    --------------------------------------json----------------------------------------------------------

    // 캘린더 폼 생성 및 수업 건수 조회
    @ResponseBody
    @GetMapping("/month")
    public List<DayLessonDTO> getMonthLessons(
            @RequestParam int year,
            @RequestParam int month,
            @RequestParam(required = false) Long trainerId
    ) {
        return ptSessionService.getLessonCalendar(year, month, trainerId);
    }


    // 선택한 날짜 수업 일정 조회
    @ResponseBody
    @GetMapping("/day")
    public List<LessonDTO> getLessonsByDate(
            @RequestParam("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
            @RequestParam(required = false) Long trainerId
    ) {
        return ptSessionService.getLessonsByDate(date, trainerId);
    }

    // 수업 등록
    @ResponseBody
    @PostMapping("/register")
    public ResponseEntity<?> registerLecture(@ModelAttribute PtSessionDTO form) {
        ptSessionService.register(form);
        return ResponseEntity.ok(Map.of("success", true));
    }

    // 수업 삭제
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

    // 트레이너들 조회
    @GetMapping("/trainers")
    @ResponseBody
    public List<TrainerDTO> getTrainerList() {
        return trainerService.getTrainerList(); // 트레이너 리스트 반환
    }

    // 트레이너에 해당하는 회원들 조회
    @GetMapping("/members")
    @ResponseBody
    public List<PtMemberDTO> getMembersByTrainer(@RequestParam Long trainerId) {
        return ptPackageService.findByTrainerId(trainerId);
    }

    // 회원 선택했을때 상품Id 반환 (PtSession 저장을 하기위해)
    @GetMapping("/package/active")
    @ResponseBody
    public Map<String, Long> getActivePackage(@RequestParam Long memberId) {
        Long packageId = ptPackageService.findPackageIdByMemberId(memberId);
        return Map.of("packageId", packageId);
    }

    // 출석 api
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
