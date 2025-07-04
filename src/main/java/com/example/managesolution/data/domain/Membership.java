package com.example.managesolution.data.domain;

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
public class Membership {
    private Long membershipId;
    @NotNull(message = "회원이 선택되지 않았습니다.")
    private Long memberId;
    private Long productId;
    private Long paymentId;
    @NotNull(message = "시작일을 입력해주세요.")
    private LocalDate startDate;
    @NotNull(message = "종료일을 입력해주세요.")
    private LocalDate endDate;
    @NotNull(message = "가격을 입력해주세요.")
    private int price; // 구매 당시 가격 (변동 가능성 대비)
    private LocalDateTime createdAt;
    private Boolean isActive;
}
