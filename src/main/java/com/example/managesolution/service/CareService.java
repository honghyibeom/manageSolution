package com.example.managesolution.service;

import com.example.managesolution.data.dto.care.response.PtCareDTO;
import com.example.managesolution.data.dto.care.response.CareDashboardDTO;
import com.example.managesolution.data.dto.care.response.ImminentCareDTO;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class CareService {

    private final PtPackageService ptPackageService;
    private final MembershipService membershipService;

    //
    public CareDashboardDTO getCareDashboard(String keyword) {
        List<PtCareDTO> ptMembers;
        List<ImminentCareDTO> membershipMembers;

        if (keyword != null && !keyword.isBlank()) {
            ptMembers = ptPackageService.findByNamePhone(keyword);
            membershipMembers = membershipService.getImminentMemberByNamePhone(keyword);
        } else {
            ptMembers = ptPackageService.getAllCareMembers();
            membershipMembers = membershipService.getImminentMemberAll();
        }

        return new CareDashboardDTO(ptMembers, membershipMembers);
    }
}
