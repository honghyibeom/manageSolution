package com.example.managesolution.mapper;

import com.example.managesolution.data.domain.PtPackage;
import com.example.managesolution.data.dto.care.response.PtCareDTO;
import com.example.managesolution.data.dto.ptSession.response.PtMemberDTO;
import com.example.managesolution.data.dto.statistics.response.PtMemberSalesDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDate;
import java.util.List;

@Mapper
public interface PtPackageMapper {
    void savePtPackage(PtPackage ptPackage);

    void updatePtPackage(PtPackage ptPackage);

    //pt 첫 수업 care
    List<PtCareDTO> getAllCareMembers();

    //pt 첫 수업 care 이름 검색
    List<PtCareDTO> findByNameContaining(@Param("keyword")String keyword);

    //pt 첫 수업 care 전화번호 검색
    List<PtCareDTO> findByPhoneContaining(@Param("keyword")String keyword);

    List<PtMemberDTO> findByTrainerId(@Param("trainerId") Long trainerId);

    void updateTrainerByMemberId (@Param("memberId") Long memberId, @Param("trainerId") Long trainerId);

    Long findPackageIdByMemberId(Long memberId);

    int decreaseRemainingCount(@Param("packageId") Long packageId);

    PtPackage findByMemberId(Long memberId);

    PtPackage findByPackageId(Long packageId);

    void updatePaymentId(@Param("memberId") Long memberId, @Param("paymentId") Long paymentId);

    List<PtMemberSalesDTO> selectPtMembers(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);

    void activateByMemberId(@Param("memberId") Long memberId);

    void deleteUnpaidPtPackages(@Param("memberId") Long memberId);

    void deletePtPackagesByMemberId(@Param("memberId") Long memberId);



}
