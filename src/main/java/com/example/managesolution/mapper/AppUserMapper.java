package com.example.managesolution.mapper;

import com.example.managesolution.data.domain.AppUser;
import com.example.managesolution.data.dto.TrainerFormDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface AppUserMapper {
    AppUser findByEmail(@Param("email") String email);

    void insert(AppUser appUser);

}
