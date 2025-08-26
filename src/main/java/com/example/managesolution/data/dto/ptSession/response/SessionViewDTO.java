package com.example.managesolution.data.dto.ptSession.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SessionViewDTO {
    private Long sessionId;
    private Long memberId;
    private Long packageId;
    private Long trainerId;
    private String batchId;
    private Boolean imminent;
    private String memberName;
    private String phone; //마지막 4자리만
    private String trainerName;
    private String productName;
    private Integer remainingCount;
    private String statusColor;
    private String label;
    private LocalTime sessionTime;
    private LocalDate sessionDate; // ✅ 날짜 그룹핑용

}
