package com.example.managesolution.service;

import com.example.managesolution.data.domain.Membership;
import com.example.managesolution.data.dto.MemberShipCareDTO;
import com.example.managesolution.data.dto.PtCareDTO;
import com.example.managesolution.mapper.MemberShipMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MembershipService {
    private final MemberShipMapper membershipMapper;

    public List<Membership> findAll() {
        return membershipMapper.findAll();
    }

    public List<MemberShipCareDTO> getAllCareMembers() {
        return membershipMapper.getAllCareMembers();
    }

    public List<MemberShipCareDTO> findByNamePhone(String keyword) {
        boolean isNumeric = keyword != null && keyword.matches("\\d+");

        if (isNumeric) {
            // 전화번호 검색
            return membershipMapper.findByPhoneContaining(keyword);
        } else {
            // 이름 검색
            return membershipMapper.findByNameContaining(keyword);
        }
    }

    public Membership findByMemberId(Long id) {
        return membershipMapper.findByMemberId(id);
    }

}
