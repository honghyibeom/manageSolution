package com.example.managesolution.mapper;

import com.example.managesolution.data.domain.Trainer;
import com.example.managesolution.data.dto.TrainerDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface TrainerMapper {

    List<TrainerDTO> getAllTrainer();

    void insert(Trainer trainer);
}
