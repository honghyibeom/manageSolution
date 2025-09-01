package com.example.managesolution.mapper;

import com.example.managesolution.data.domain.Trainer;
import com.example.managesolution.data.dto.dashboard.response.MonthCountDTO;
import com.example.managesolution.data.dto.trainer.request.TrainerFormDTO;
import com.example.managesolution.data.dto.trainer.response.TrainerDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface TrainerMapper {
    // 트레이너 리스트 조회
    List<TrainerDTO> getAllTrainer();
    // 트레이너 추가
    void insert(Trainer trainer);
    // 모든 트레이너 이름 조회
    List<String> findAllTrainerNames();
    // 트레이너별 회원 수 6개월 통계 조회
    List<MonthCountDTO> getMonthlyMemberCountsRaw(@Param("trainerName") String trainerName);
    // 트레이너 폼 조회
    TrainerFormDTO findTrainerById(Long trainerId);
    // 트레이너 수정
    void update(Trainer trainer);
}
