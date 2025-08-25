package com.example.managesolution.data.dto.ptSession.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SessionSummaryDTO {
    private String label;
    private Long value;
    private Boolean isPercent;
}
