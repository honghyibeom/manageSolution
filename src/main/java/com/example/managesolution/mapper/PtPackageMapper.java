package com.example.managesolution.mapper;

import com.example.managesolution.data.domain.PtPackage;
import com.example.managesolution.data.dto.PtCareDTO;
import com.example.managesolution.data.dto.PtMemberDTO;
import com.example.managesolution.data.dto.PtMemberSalesDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDate;
import java.util.List;

@Mapper
public interface PtPackageMapper {
    void savePtPackage(PtPackage ptPackage);

    void deactivateByMemberId(Long memberId);

    void updatePtPackage(PtPackage ptPackage);

    List<PtCareDTO> getAllCareMembers();

    List<PtCareDTO> findByNameContaining(@Param("keyword")String keyword);

    List<PtCareDTO> findByPhoneContaining(@Param("keyword")String keyword);

    List<PtMemberDTO> findByTrainerId(@Param("trainerId") Long trainerId);

    void updateTrainerByMemberId (@Param("memberId") Long memberId, @Param("trainerId") Long trainerId);

    Long findPackageIdByMemberId(Long memberId);

    int increaseRemainingCount(@Param("packageId") Long packageId);

    PtPackage findByMemberId(Long memberId);

    void updatePaymentId(@Param("memberId") Long memberId, @Param("paymentId") Long paymentId);

    List<PtMemberSalesDTO> selectPtMembers(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);



}
