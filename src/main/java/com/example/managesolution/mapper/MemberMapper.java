package com.example.managesolution.mapper;

import com.example.managesolution.data.domain.Member;
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
}
