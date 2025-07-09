package com.example.managesolution.mapper;

import com.example.managesolution.data.domain.Member;
import com.example.managesolution.data.dto.MemberExpiredDTO;
import com.example.managesolution.data.dto.MemberUnpaidDTO;
import com.example.managesolution.data.dto.PaymentHistoryDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface MemberMapper {
    List<Member> findPaged(@Param("limit") int limit, @Param("offset") int offset);
    int countAll();

    Member findById(Long id);

    void insert(Member member);

    void update(Member member);

    void delete(Long id);

    // 전화번호 검색
    List<Member> findByPhoneContaining(@Param("keyword")String keyword,
                                       @Param("limit")int limit,
                                       @Param("offset")int offset);

    // 폰 번호 검색
    List<Member> findByNameContaining(@Param("keyword")String keyword,
                                       @Param("limit")int limit,
                                       @Param("offset")int offset);

    List<Member> findByStatus(@Param("status")String status,
                              @Param("limit")int limit,
                              @Param("offset")int offset);

    List<Member> findByStatusAndPhone(@Param("status")String status,
                                        @Param("keyword")String keyword,
                                        @Param("limit")int limit,
                                        @Param("offset")int offset);

    List<Member> findByStatusAndName(@Param("status")String status,
                                      @Param("keyword")String keyword,
                                      @Param("limit")int limit,
                                      @Param("offset")int offset);

    List<MemberUnpaidDTO> findUnpaidMembers(@Param("keyword") String keyword);

    List<MemberExpiredDTO> findExpiredMembers(@Param("keyword") String keyword);

    void updateStatusActive(Long memberId);


}
