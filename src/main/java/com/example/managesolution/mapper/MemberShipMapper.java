package com.example.managesolution.mapper;

import com.example.managesolution.data.domain.Membership;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MemberShipMapper {
    List<Membership> findAll();
}
