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

    // 상품(Subscription) 정보
    @NotBlank(message = "상품 종류를 선택하세요.")
    private String productType;   // MEMBERSHIP or PT

    private Long productId;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate startDate;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate endDate;

    private Integer price;

    // PT 전용
    private Long trainerId;
    private Integer totalCount;
    private Integer remainingCount;
}
