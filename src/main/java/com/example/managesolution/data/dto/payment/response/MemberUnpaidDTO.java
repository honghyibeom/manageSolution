package com.example.managesolution.data.dto.payment.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MemberUnpaidDTO {
    private Long memberId;
    private String memberName;
    private String phone;
    private String productName;
    private String productType;
    private Integer amount;
    private Long productId;
}
