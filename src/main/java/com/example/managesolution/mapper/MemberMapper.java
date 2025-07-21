package com.example.managesolution.mapper;

import com.example.managesolution.data.domain.Member;
import com.example.managesolution.data.dto.dashboard.response.MemberStatsDTO;
import com.example.managesolution.data.dto.member.response.MemberProductDTO;
import com.example.managesolution.data.dto.payment.response.MemberExpiredDTO;
import com.example.managesolution.data.dto.payment.response.MemberUnpaidDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface MemberMapper {
    List<MemberProductDTO> findPaged(@Param("limit") int limit, @Param("offset") int offset);
    int countAll();

    Member findById(Long id);

    void insert(Member member);

    void update(Member member);

    void delete(Long id);

    // 전화번호 검색
    List<MemberProductDTO> findByPhoneContaining(@Param("keyword")String keyword,
                                       @Param("limit")int limit,
                                       @Param("offset")int offset);

    // 폰 번호 검색
    List<MemberProductDTO> findByNameContaining(@Param("keyword")String keyword,
                                       @Param("limit")int limit,
                                       @Param("offset")int offset);

    List<MemberProductDTO> findByStatus(@Param("status")String status,
                              @Param("limit")int limit,
                              @Param("offset")int offset);

    List<MemberProductDTO> findByStatusAndPhone(@Param("status")String status,
                                        @Param("keyword")String keyword,
                                        @Param("limit")int limit,
                                        @Param("offset")int offset);

    List<MemberProductDTO> findByStatusAndName(@Param("status")String status,
                                      @Param("keyword")String keyword,
                                      @Param("limit")int limit,
                                      @Param("offset")int offset);

    List<MemberUnpaidDTO> findUnpaidMembers(@Param("keyword") String keyword);

    List<MemberExpiredDTO> findExpiredMembers(@Param("keyword") String keyword);

    void updateStatusActive(Long memberId);

    MemberStatsDTO getMemberStats();


}
