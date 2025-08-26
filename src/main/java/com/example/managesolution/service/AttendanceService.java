package com.example.managesolution.service;

import com.example.managesolution.data.domain.Attendance;
import com.example.managesolution.data.dto.ResponseDTO;
import com.example.managesolution.data.dto.ptSession.request.AttendanceRequestDTO;
import com.example.managesolution.exception.CustomException;
import com.example.managesolution.exception.ErrorCode;
import com.example.managesolution.mapper.AttendanceMapper;
import com.example.managesolution.mapper.PtPackageMapper;
import com.example.managesolution.mapper.PtSessionMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

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


}
