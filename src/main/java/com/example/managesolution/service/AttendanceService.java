package com.example.managesolution.service;

import com.example.managesolution.data.domain.Attendance;
import com.example.managesolution.data.dto.ResponseDTO;
import com.example.managesolution.data.dto.attendance.AttendanceChartDTO;
import com.example.managesolution.data.dto.attendance.AttendanceReportDTO;
import com.example.managesolution.data.dto.attendance.DormantMemberDTO;
import com.example.managesolution.data.dto.ptSession.request.AttendanceRequestDTO;
import com.example.managesolution.mapper.AttendanceMapper;
import com.example.managesolution.mapper.PtSessionMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AttendanceService {

    private final AttendanceMapper attendanceMapper;
    private final PtSessionMapper ptSessionMapper;

    // pt 출석 api
    @Transactional
    public ResponseDTO attend(AttendanceRequestDTO dto) {
        String status = ptSessionMapper.getStatus(dto.getSessionId());

        if (status.equals("BOOKED")) {
            // 1. 출석 기록 추가
            Attendance attendance = Attendance.builder()
                    .memberId(dto.getMemberId())
                    .sessionId(dto.getSessionId())
                    .attendedAt(LocalDateTime.now())
                    .status(dto.getStatus())
                    .build();
            attendanceMapper.insertAttendance(attendance);
        }

        String message;
        if (dto.getStatus().equals("ATTENDED")) {
            message = "출석";
        }
        else {
            message = "결석";
        }

        // 2. 상태 변경
        ptSessionMapper.updateStatus(dto.getSessionId(), dto.getStatus());


        return ResponseDTO.builder()
                .message(message +" 처리 되었습니다.")
                .build();
    }

    public List<AttendanceReportDTO> findReport(String start, String end, String keyword) {
        return attendanceMapper.findAttendanceReport(start, end, keyword);
    }

    public AttendanceChartDTO getChartData() {
        List<Map<String, Object>> weekly = attendanceMapper.findWeeklyAttendance();
        List<Map<String, Object>> time = attendanceMapper.findTimeAttendance();

        AttendanceChartDTO dto = new AttendanceChartDTO();
        dto.setWeeklyCounts(buildWeekly(weekly));
        dto.setTimeCounts(buildTime(time));
        return dto;
    }

    public List<DormantMemberDTO> findDormantMembers() {
        return attendanceMapper.findDormantMembers();
    }
    private List<Integer> buildWeekly(List<Map<String, Object>> rows) {
        // 요일: 1=일, 2=월 ... 7=토
        Map<Integer, Integer> map = rows.stream()
                .collect(Collectors.toMap(
                        r -> ((Number) r.get("dayOfWeek")).intValue(),
                        r -> ((Number) r.get("count")).intValue()
                ));

        List<Integer> result = new ArrayList<>();
        for (int i = 2; i <= 7; i++) { // 월(2)~토(7)
            result.add(map.getOrDefault(i, 0));
        }
        result.add(map.getOrDefault(1, 0)); // 일요일(1) 마지막에 넣기
        return result;
    }

    private List<Integer> buildTime(List<Map<String, Object>> rows) {
        Map<Integer, Integer> map = rows.stream()
                .collect(Collectors.toMap(
                        r -> ((Number) r.get("hourSlot")).intValue(),
                        r -> ((Number) r.get("count")).intValue()
                ));

        List<Integer> result = new ArrayList<>();
        int[] slots = {6, 9, 12, 15, 18, 21};
        for (int h : slots) {
            result.add(map.getOrDefault(h, 0));
        }
        return result;
    }

}
