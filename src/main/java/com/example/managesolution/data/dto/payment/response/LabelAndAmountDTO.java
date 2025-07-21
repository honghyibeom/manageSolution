package com.example.managesolution.data.dto.payment.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LabelAndAmountDTO {
    private String label;
    private Long amount;
}
