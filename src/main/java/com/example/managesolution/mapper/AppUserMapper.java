package com.example.managesolution.mapper;

import com.example.managesolution.data.domain.AppUser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface AppUserMapper {
    // 이메일로 사용자 찾기
    AppUser findByEmail(@Param("email") String email);
    // 사용자(트레이너) 등록
    void insert(AppUser appUser);
    // 사용자(트레이너) 수정
    void update(AppUser appUser);
    // 사용자(트레이너) 삭제
    void deleteById(Long id);

}
