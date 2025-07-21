package com.example.managesolution.data.dto.care.response;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@Builder
public class PtCareDTO {
    String memberName;
    String memberPhone;
    Integer count;
    Integer totalCount;
    LocalDate sessionDate;
    LocalTime sessionTime;
}
