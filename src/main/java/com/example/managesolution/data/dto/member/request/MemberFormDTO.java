package com.example.managesolution.data.dto.member.request;

import com.example.managesolution.data.enumerate.Status;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MemberFormDTO {
    // 회원 정보
    private Long memberId;

    @NotBlank(message = "이름은 필수입니다.")
    private String name;
    @NotBlank(message = "전화번호는 필수입니다.")
    @Pattern(regexp = "^010-\\d{4}-\\d{4}$", message = "전화번호 형식이 올바르지 않습니다.")
    private String phone;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @NotNull(message = "생년월일을 입력하세요.")
    @Past(message = "생년월일은 과거 날짜여야 합니다.")
    private LocalDate birthDate;
    @NotBlank(message = "성별을 선택하세요.")
    private String gender;
    private Status status;
    private String memo;

    // 선택된 상품 타입
    private String productType;

    // 회원권 정보
    private Long membershipProductId;
    @FutureOrPresent(message = "과거로 설정할 수 없습니다.")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate membershipStartDate;
    @FutureOrPresent(message = "과거로 설정할 수 없습니다.")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate membershipEndDate;
    private Integer membershipPrice;

    // PT 패키지 정보
    private Long ptProductId;
    private Long trainerId;
    @FutureOrPresent(message = "과거로 설정할 수 없습니다.")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate ptStartDate;
    @FutureOrPresent(message = "과거로 설정할 수 없습니다.")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate ptEndDate;
    private Integer ptTotalCount;
    private Integer ptPrice;
}
