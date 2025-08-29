package com.example.managesolution.controller;

import com.example.managesolution.data.dto.ResponseDTO;
import com.example.managesolution.data.dto.ptSession.request.AttendanceRequestDTO;
import com.example.managesolution.data.dto.ptSession.request.RegisterRangeFormDTO;
import com.example.managesolution.data.dto.ptSession.request.SessionUpdateDTO;
import com.example.managesolution.data.dto.ptSession.response.*;
import com.example.managesolution.data.dto.trainer.response.TrainerDTO;
import com.example.managesolution.service.*;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequiredArgsConstructor
@RequestMapping("/lecture")
@Log4j2
public class PtSessionController {
    private final PtSessionService ptSessionService;
    private final AttendanceService attendanceService;
    private final TrainerService trainerService;
    private final SubscriptionService subscriptionService;

    // new 수업 관리 페이지 조회
    @GetMapping("/new")
    public String newClass(@ModelAttribute("cond") LectureSearchDTO lectureSearchDTO,
                           @RequestParam(defaultValue = "1") int page,
                           Model model) {

        // ✅ 기본값 보정
        LectureSearchDTO cond = ptSessionService.applyDefaults(lectureSearchDTO);

        int pageSize = 10;
        List<SessionViewDTO> sessionView = ptSessionService.getSessionView(cond, page, pageSize);
        int totalCount = sessionView.size();
        int totalPages = (int) Math.ceil((double) totalCount / pageSize);

        List<SessionSummaryDTO> summary = ptSessionService.getSummeryList(sessionView);
        List<DateGroupDTO> dateGroup = ptSessionService.getDateGroup(sessionView);

        model.addAttribute("trainers", trainerService.getTrainerList());
        model.addAttribute("summaryList", summary);
        model.addAttribute("dateGroups", dateGroup);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("currentPage", page);

        // ✅ 뷰에서 조건 유지
        model.addAttribute("cond", cond);

        return "lecture/newList";
    }

    // new 수업 등록 페이지 조회
    @GetMapping("/form")
    public String form(Model model) {
        model.addAttribute("trainers", trainerService.getTrainerList());
        return "lecture/form";
    }

    //new 트레이너별 회원 조회
    @GetMapping("/{id}")
    public String trainerDetail(@PathVariable Long id, Model model) {
        model.addAttribute("trainers", trainerService.getTrainerList());
        model.addAttribute("ptMembers", subscriptionService.findByTrainerId(id));
        model.addAttribute("selectedTrainerId", id);
        return "lecture/form";
    }

    //new session update
    @PostMapping("/sessions/update")
    public String updateSession(@ModelAttribute SessionUpdateDTO dto) {
        ptSessionService.updateSession(dto);

        // 저장 후 수업 리스트 페이지로 리다이렉트
        return "redirect:/lecture/new"; // <-  페이지 경로가 아니라 url을 입력해야 됐어...
    }

    // new session delete
    @PostMapping("/sessions/delete")
    public String deleteSession(@RequestParam("sessionId") Long sessionId,
                                RedirectAttributes redirectAttributes) {
        ptSessionService.deleteByIds(sessionId); // DB 삭제 or 상태 변경
        redirectAttributes.addFlashAttribute("message", "수업이 취소되었습니다.");
        return "redirect:/lecture/new"; // 다시 목록 페이지로 리다이렉트
    }

//    --------------------------------------json----------------------------------------------------------

    // new 트레이너의 스케줄 date와 time api
    @GetMapping("/trainers/{trainerId}/busy")
    @ResponseBody
    public Map<String, List<String>> getBusy(@PathVariable Long trainerId,
                                             @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate start,
                                             @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate end) {
        return ptSessionService.getBusyMap(trainerId, start, end);
    }

    //new 수업 등록
    @PostMapping(value = "/register", consumes = "application/json", produces = "application/json")
    public ResponseEntity<ResponseDTO> registerJson(@RequestBody @Valid RegisterRangeFormDTO form) {
        return ResponseEntity.ok(ptSessionService.registerRangeResponse(form));
    }

    // new 출석
    @PostMapping("/sessions/status")
    @ResponseBody
    public ResponseEntity<ResponseDTO> updateAttendance(@RequestBody AttendanceRequestDTO request) {
        return ResponseEntity.ok(attendanceService.attend(request));
    }

    // new 수업 일괄 삭제
    @PostMapping("/sessions/group/delete/{batchId}")
    @ResponseBody
    public ResponseEntity<ResponseDTO> deleteGroup(@PathVariable("batchId") String batchId) {
        return ResponseEntity.ok(ptSessionService.deleteGroupSession(batchId));
    }

    // new 수업 그룹 조회
    @GetMapping("/sessions/group/{batchId}")
    @ResponseBody
    public ResponseEntity<ResponseDTO> getGroup(@PathVariable("batchId") String batchId) {
        return ResponseEntity.ok(ptSessionService.getGroupSession(batchId));
    }

}
