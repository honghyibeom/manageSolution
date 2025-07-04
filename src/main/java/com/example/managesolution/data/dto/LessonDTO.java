package com.example.managesolution.data.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@Builder
public class LessonDTO {
    private Long sessionId;
    private Long memberId;
    private Long packageId;
    private String memberName;
    private String trainerName;
    private LocalDate sessionDate;
    private LocalTime sessionTime;
    private boolean attended;
    private String status;
}
