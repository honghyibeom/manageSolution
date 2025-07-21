package com.example.managesolution.data.dto.dashboard.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductRankingDTO {
    private Integer rank;
    private String productName;
    private Integer count;
}
