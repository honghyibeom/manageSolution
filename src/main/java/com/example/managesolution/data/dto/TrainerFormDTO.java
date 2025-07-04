package com.example.managesolution.data.dto;

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
public class TrainerFormDTO {
    private Long trainerId;
    private String email;
    private String password;
    private String name;
    private String phone;
    private String role; // ADMIN or Trainer
    private LocalDate birthDate;
    private String gender;
    private Integer payPerSession;
    private Integer baseSalary;
    private Integer careerYears;
    private LocalDateTime updatedAt;
    private LocalDateTime createdAt;
    private String passwordConfirm;
}
