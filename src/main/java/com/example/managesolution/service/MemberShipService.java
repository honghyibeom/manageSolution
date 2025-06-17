package com.example.managesolution.service;

import com.example.managesolution.data.domain.Membership;
import com.example.managesolution.mapper.MemberShipMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MemberShipService {
    private final MemberShipMapper membershipMapper;

    public List<Membership> findAll() {
        return membershipMapper.findAll();
    }

}
