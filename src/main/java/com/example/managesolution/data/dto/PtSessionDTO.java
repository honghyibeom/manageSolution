package com.example.managesolution.data.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class PtSessionDTO {
    private Long trainerId;
    private Long memberId;
    private Long packageId;
    private String sessionDate;
    private String sessionTime;

    // 반복 관련
    private boolean repeat;
    private List<Integer> repeatDays; // ex: [1, 3, 5]
    private int repeatWeeks;
}
