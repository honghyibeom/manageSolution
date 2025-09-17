package com.example.managesolution.data.dto.attendance;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AttendanceReportDTO {
    private LocalDate sessionDate;
    private LocalTime sessionTime;
    private String memberName;
    private String trainerName;
    private String productName;
    private String status;
}
