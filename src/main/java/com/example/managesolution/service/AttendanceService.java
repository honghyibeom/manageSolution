package com.example.managesolution.service;

import com.example.managesolution.data.domain.Attendance;
import com.example.managesolution.exception.CustomException;
import com.example.managesolution.exception.ErrorCode;
import com.example.managesolution.mapper.AttendanceMapper;
import com.example.managesolution.mapper.PtPackageMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AttendanceService {

    private final AttendanceMapper attendanceMapper;
    private final PtPackageMapper ptPackageMapper;

    // pt 출석 api
    @Transactional
    public void attend(Long memberId, Long sessionId, Long packageId, String status) {
        // 1. 출석 기록 추가
        Attendance attendance = Attendance.builder()
                .memberId(memberId)
                .sessionId(sessionId)
                .attendedAt(LocalDateTime.now())
                .status(status)
                .build();
        attendanceMapper.insertAttendance(attendance);

        // 2. pt 횟수 증가 (remainingCount + 1)
        int updated = ptPackageMapper.increaseRemainingCount(packageId);
        if (updated == 0) {
            throw new CustomException(ErrorCode.EXCEED_REMAINING_COUNT);
        }
    }


}
