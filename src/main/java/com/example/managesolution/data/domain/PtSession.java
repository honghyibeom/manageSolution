package com.example.managesolution.data.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalTime;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PtSession {
    private Long ptSessionId;
    private Long memberId;
    private Long trainerId;
    private Long subscriptionId;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate sessionDate;
    @DateTimeFormat(pattern = "HH:mm")
    private LocalTime sessionTime;
    private String batchId;
}
