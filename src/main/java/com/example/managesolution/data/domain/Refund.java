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
public class Refund {
    private Long refundId;
    private Long paymentId;
    private Integer amount;
    private String reason;
    private LocalDateTime refundDate;
}
