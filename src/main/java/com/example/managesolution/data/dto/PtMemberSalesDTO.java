package com.example.managesolution.data.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Builder
@Data
public class PtMemberSalesDTO {
    private String name;
    private Integer totalCount;
    private Integer price;
    private LocalDate date;
}
