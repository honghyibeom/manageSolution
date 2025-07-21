package com.example.managesolution.data.dto.care.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CareDashboardDTO {
    private List<PtCareDTO> ptMembers;
    private List<ImminentCareDTO> membershipMembers;
}
