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
public class Subscription {
    private Long subscriptionId;

    private Long memberId;
    private Long productId;
    private String productType;

    // PT 전용 (MEMBERSHIP은 null)
    private Long trainerId;

    private Long paymentId;

    private LocalDate startDate;
    private LocalDate endDate;

    // PT 전용
    private Integer totalCount;
    private Integer remainingCount;

    private Integer price;

    private Boolean isActive;
    private LocalDateTime createdAt;
}
