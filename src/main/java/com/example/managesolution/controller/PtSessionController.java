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
        List<DateGroupDTO> dateGroup = ptSessionService.getDateGroup(cond, sessionView);

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
        model.addAttribute("ptMembers", ptPackageService.findByTrainerId(id));
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
//    @ResponseBody
//    @PostMapping("/register")
//    public ResponseEntity<?> registerLecture(@ModelAttribute PtSessionDTO form) {
//        ptSessionService.register(form);
//        return ResponseEntity.ok(Map.of("success", true));
//    }

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
    public ResponseEntity<ResponseDTO> attend(@RequestBody AttendanceRequestDTO dto) {
        return ResponseEntity.ok(attendanceService.attend(dto));
    }
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

}
