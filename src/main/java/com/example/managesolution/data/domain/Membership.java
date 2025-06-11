package com.example.managesolution.data.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Membership {
    private Long membershipId;
    private Long memberId;
    private LocalDate startDate;
    private LocalDate endDate;
    private Boolean isActive;
}
