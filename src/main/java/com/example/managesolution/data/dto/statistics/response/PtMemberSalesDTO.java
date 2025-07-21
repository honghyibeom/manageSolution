package com.example.managesolution.data.dto.statistics.response;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class PtMemberSalesDTO {
    private String productName;
    private Integer count;
    private Integer unitPrice;
    private Integer totalAmount;
}
