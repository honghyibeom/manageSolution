package com.example.managesolution.data.dto.payment.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PaymentDTO {
    private Long memberId;
    private Long subscriptionId;
    private Long productId;
    private Integer amount;
    private String productType;
    private String method;
}
