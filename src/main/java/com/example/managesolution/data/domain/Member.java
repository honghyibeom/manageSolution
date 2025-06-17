package com.example.managesolution.data.domain;

import com.example.managesolution.data.enumerate.Status;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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
public class Member {
    private Long memberId;
    @NotBlank(message = "이름을 입력해주세요.")
    private String name;
    @NotBlank(message = "전화번호를 입력해주세요.")
    private String phone;
    @NotNull(message = "생년월일을 입력해주세요.")
    private LocalDate birthDate;
    @NotBlank(message = "성별을 선택해주세요.")
    private String gender;
    private Status status;
    private String memo;
    private LocalDateTime createdAt;
}
