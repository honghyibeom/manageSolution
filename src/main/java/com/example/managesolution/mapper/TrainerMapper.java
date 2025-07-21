package com.example.managesolution.mapper;

import com.example.managesolution.data.domain.Trainer;
import com.example.managesolution.data.dto.dashboard.response.MonthCountDTO;
import com.example.managesolution.data.dto.trainer.response.TrainerDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface TrainerMapper {

    List<TrainerDTO> getAllTrainer();

    void insert(Trainer trainer);

    List<String> findAllTrainerNames();

    List<MonthCountDTO> getMonthlyMemberCountsRaw(@Param("trainerName") String trainerName);
}
