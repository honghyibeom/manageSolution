package com.example.managesolution.data.dto.trainer.request;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TrainerFormDTO {
    private Long trainerId;
    @NotBlank(message = "이메일을 입력해주세요.")
    @Email(message = "올바른 이메일 형식이 아닙니다.")
    private String email;
    @NotBlank(message = "비밀번호를 입력해주세요.")
    @Size(min = 8, max = 20, message = "비밀번호는 8~20자여야 합니다.")
    private String password;
    @NotBlank(message = "이름을 입력해주세요.")
    private String name;
    @NotBlank(message = "전화번호를 입력해주세요.")
    @Pattern(regexp = "\\d{11}", message = "전화번호 형식은 010-0000-0000이어야 합니다.")
    private String phone;
    private String role; // ADMIN or Trainer

    @NotNull(message = "생년월일을 선택해주세요.")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Past(message = "생년월일은 과거여야 합니다.")
    private LocalDate birthDate;
    @NotBlank(message = "성별을 선택해주세요.")
    @Pattern(regexp = "남성|여성", message = "성별은 남성 또는 여성이어야 합니다.")
    private String gender;
    @NotNull(message = "세션당 급여를 입력해주세요.")
    @PositiveOrZero(message = "세션당 급여는 0 이상이어야 합니다.")
    private Integer payPerSession;
    @NotNull(message = "기본 급여를 입력해주세요.")
    @PositiveOrZero(message = "기본 급여는 0 이상이어야 합니다.")
    private Integer baseSalary;
    private Integer careerYears;
    private LocalDateTime updatedAt;
    private LocalDateTime createdAt;
    @NotBlank(message = "비밀번호 확인을 입력해주세요.")
    private String passwordConfirm;
}
