package com.example.managesolution.mapper;

import com.example.managesolution.data.domain.Membership;
import com.example.managesolution.data.dto.care.response.ImminentCareDTO;
import com.example.managesolution.data.dto.statistics.response.MembershipSalesDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDate;
import java.util.List;

@Mapper
public interface MemberShipMapper {
    List<Membership> findAll();
    void saveMembership(Membership membership);

    void deactivateByMemberId(Long memberId);

    void updateMembership(Membership membership);

    List<ImminentCareDTO> getAllCareMembers();

    List<ImminentCareDTO> findByNameContaining(@Param("keyboard") String keyboard);

    List<ImminentCareDTO> findByPhoneContaining(@Param("keyboard") String keyboard);

    Membership findByMemberId(Long memberId);

    void updatePaymentId(@Param("memberId") Long memberId, @Param("paymentId") Long paymentId);

    List<MembershipSalesDTO> selectMembershipMembers(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);

    List<ImminentCareDTO> findExpiringMemberAll();

    List<ImminentCareDTO> findExpiringMemberByPhone(@Param("keyword") String keyword);

    List<ImminentCareDTO> findExpiringMemberByName(@Param("keyword") String keyword);

    void activateByMemberId(@Param("memberId") Long memberId);

    void deleteUnpaidMembership(@Param("memberId") Long memberId);

    void deleteMemberShipByMemberId(@Param("memberId") Long memberId);
}
