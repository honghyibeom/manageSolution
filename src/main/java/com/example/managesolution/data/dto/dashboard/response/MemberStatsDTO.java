package com.example.managesolution.data.dto.dashboard.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MemberStatsDTO {
    private Integer totalMembers;
    private Integer totalMale;
    private Integer totalFemale;

    private Integer expiredMembers;
    private Integer expiredMale;
    private Integer expiredFemale;

    private Integer newMembers;
    private Integer newMale;
    private Integer newFemale;

    private Integer todayVisitedMembers;
    private Integer todayVisitedMale;
    private Integer todayVisitedFemale;
}
