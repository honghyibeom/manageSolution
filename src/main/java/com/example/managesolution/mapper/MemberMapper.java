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
    List<MemberProductDTO> findPaged(@Param("limit") int limit, @Param("offset") int offset);

    int countAll();

    Member findById(Long id);

    //회원 수정시 보여지는 폼
    MemberFormDTO findFormById(Long memberId);

    void insert(Member member);

    void update(Member member);

    void delete(Long id);

    List<MemberProductDTO> findMembers(@Param("status")String status,
                                      @Param("keyword")String keyword,
                                      @Param("limit")int limit,
                                      @Param("offset")int offset,
                                       @Param("isNumeric") boolean isNumeric);

    List<MemberUnpaidDTO> findUnpaidMembers(@Param("keyword") String keyword,
                                            @Param("startDate") LocalDate startDate,
                                            @Param("endDate") LocalDate endDate);

    List<MemberExpiredDTO> findExpiredMembers(@Param("keyword") String keyword,
                                              @Param("startDate") LocalDate startDate,
                                              @Param("endDate") LocalDate endDate);

    void updateStatusActive(Long memberId);

    MemberStatsDTO getMemberStats();


}
