package com.example.managesolution.service;

import com.example.managesolution.data.domain.Membership;
import com.example.managesolution.data.dto.care.response.ImminentCareDTO;
import com.example.managesolution.mapper.MemberShipMapper;
import com.example.managesolution.mapper.PtPackageMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MembershipService {
    private final MemberShipMapper membershipMapper;
    private final PtPackageMapper ptPackageMapper;

    public Membership findByMemberId(Long id) {
        return membershipMapper.findByMemberId(id);
    }

    public List<ImminentCareDTO> getImminentMemberAll() {
        return membershipMapper.findExpiringMemberAll();
    }

    public List<ImminentCareDTO> getImminentMemberByNamePhone(String keyword) {
        boolean isNumeric = keyword != null && keyword.matches("\\d+");

        if (isNumeric) {
            // 전화번호 검색
            return membershipMapper.findExpiringMemberByPhone(keyword);
        } else {
            // 이름 검색
            return membershipMapper.findExpiringMemberByName(keyword);
        }
    }

    public void deleteUnpaidMemberShip(Long memberId) {
        membershipMapper.deleteUnpaidMembership(memberId);
    }

    public void deleteExpiredMembershipAndPtPackage(Long memberId) {
        membershipMapper.deleteMemberShipByMemberId(memberId);
        ptPackageMapper.deletePtPackagesByMemberId(memberId);
    }

}
