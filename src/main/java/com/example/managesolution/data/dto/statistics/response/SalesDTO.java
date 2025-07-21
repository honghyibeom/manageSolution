package com.example.managesolution.data.dto.statistics.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SalesDTO {
    Long totalSales;
    Long monthlySales;
    Long dailySales;
}
