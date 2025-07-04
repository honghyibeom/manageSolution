package com.example.managesolution.mapper;

import com.example.managesolution.data.domain.Membership;
import com.example.managesolution.data.dto.MemberShipCareDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface MemberShipMapper {
    List<Membership> findAll();
    void saveMembership(Membership membership);

    void deactivateByMemberId(Long memberId);

//    void updateMembership(Membership membership);

    List<MemberShipCareDTO> getAllCareMembers();

    List<MemberShipCareDTO> findByNameContaining(@Param("keyboard") String keyboard);

    List<MemberShipCareDTO> findByPhoneContaining(@Param("keyboard") String keyboard);

    Membership findByMemberId(Long memberId);

    void updatePaymentId(@Param("memberId") Long memberId, @Param("paymentId") Long paymentId);
}
