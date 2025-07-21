package com.example.managesolution.data.dto.trainer.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TrainerDTO {
    private Long trainerId;
    private String name;
    private String phone;
    private LocalDate birthDate;
    private String email;
    private String gender;
    private Integer payPerSession; // pt 1회 수당
    private Integer baseSalary; // 기본급
    private Integer careerYears;
}
