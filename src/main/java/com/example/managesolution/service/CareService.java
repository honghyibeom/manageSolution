package com.example.managesolution.service;

import com.example.managesolution.data.dto.care.response.PtCareDTO;
import com.example.managesolution.data.dto.care.response.CareDashboardDTO;
import com.example.managesolution.data.dto.care.response.ImminentCareDTO;
import com.example.managesolution.mapper.SubscriptionMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
@AllArgsConstructor
public class CareService {
    private final SubscriptionMapper subscriptionMapper;

    public CareDashboardDTO getCareDashboard(String keyword) {
        List<PtCareDTO> ptMembers;
        List<ImminentCareDTO> membershipMembers;

        ptMembers = subscriptionMapper.findFirstPtByPtMembers(keyword);
        membershipMembers = subscriptionMapper.findImminentByMemberships(keyword);
        return new CareDashboardDTO(ptMembers, membershipMembers);
    }
}
