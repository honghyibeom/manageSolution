package com.example.managesolution.mapper;

import com.example.managesolution.data.domain.Member;
import com.example.managesolution.data.dto.dashboard.response.MemberStatsDTO;
import com.example.managesolution.data.dto.member.request.MemberFormDTO;
import com.example.managesolution.data.dto.member.response.MemberProductDTO;
import com.example.managesolution.data.dto.payment.response.MemberExpiredDTO;
import com.example.managesolution.data.dto.payment.response.MemberUnpaidDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDate;
import java.util.List;

@Mapper
public interface MemberMapper {
    // 총 회원 수
    int countAll();
    // 회원 찾기
    Member findById(Long id);
    // 회원 수정시 보여지는 폼
    MemberFormDTO findFormById(Long memberId);
    // 회원 등록
    void insert(Member member);
    // 회원 수정
    void update(Member member);
    // 회원 삭제
    void delete(Long id);
    // 회원 조회
    List<MemberProductDTO> findMembers(@Param("status")String status,
                                      @Param("keyword")String keyword,
                                      @Param("limit")int limit,
                                      @Param("offset")int offset,
                                       @Param("isNumeric") boolean isNumeric);
    //미결제 회원 조회
    List<MemberUnpaidDTO> findUnpaidMembers(@Param("keyword") String keyword,
                                            @Param("startDate") LocalDate startDate,
                                            @Param("endDate") LocalDate endDate);
    // 만료된 회원 조회
    List<MemberExpiredDTO> findExpiredMembers(@Param("keyword") String keyword,
                                              @Param("startDate") LocalDate startDate,
                                              @Param("endDate") LocalDate endDate);
    // 회원 상태 true
    void updateStatusActive(Long memberId);
    //회원 통계
    MemberStatsDTO getMemberStats();


}
