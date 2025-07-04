package com.example.managesolution.data.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Payment {
    private Long PaymentId;
    private Long memberId;
    private Long productId;
    private Integer amount;
    private String method; // CASH or CARD
    private LocalDateTime paidAt;
}
