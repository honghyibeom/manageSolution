package com.example.managesolution.mapper;

import com.example.managesolution.data.domain.AppUser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface AppUserMapper {
    AppUser findByEmail(@Param("email") String email);

    void insert(AppUser appUser);

    void update(AppUser appUser);

    void deleteById(Long id);

}
