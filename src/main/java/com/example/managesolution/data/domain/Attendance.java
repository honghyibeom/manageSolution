package com.example.managesolution.data.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Attendance {
    private Long attendanceId;
    private Long memberId;
    private LocalDateTime checkIn;
    private LocalDateTime checkOut;
}
