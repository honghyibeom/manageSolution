package com.example.managesolution.data.dto.ptSession.response;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class PtMemberDTO {
    private Long subscriptionId;
    private Long memberId;
    private String name;
    private String phone;
    private LocalDate birth;
    private int remainingCount;
    private int totalCount;
    private LocalDate startDate;
    private LocalDate endDate;
}
