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
public class PtPackage {
    private Long packageId;
    private Long memberId;
    private Long trainerId;
    private Long productId;
    private Long paymentId;
    private Integer totalCount; // 구매한 pt 횟수
    private Integer remainingCount; // 사용한 pt 횟수
    private LocalDate startDate;
    private LocalDate endDate;
    private Integer price; // 구배 당시 가격 (변동 가능성 대비)
    private LocalDateTime createdAt;
    private Boolean isActive;

}
