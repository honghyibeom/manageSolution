package com.example.managesolution.data.dto.statistics.response;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class StatisticsResponseDTO {
    private List<String> labels;
    private List<Long> salesData;
    private List<PtMemberSalesDTO> ptMembers;
    private List<MembershipSalesDTO> membershipMembers;
    private SummaryDTO ptSummary;
    private SummaryDTO membershipSummary;
}
