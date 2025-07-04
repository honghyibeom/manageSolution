package com.example.managesolution.data.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Trainer {
    private Long trainerId;
    private LocalDate birthDate;
    private String gender;
    private String phone;
    private Integer payPerSession; // pt 1회 수당
    private Integer baseSalary; // 기본급
    private Integer careerYears; // 경력
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

}
