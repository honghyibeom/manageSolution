package com.example.managesolution.service;

import com.example.managesolution.data.domain.AppUser;
import com.example.managesolution.data.domain.Trainer;
import com.example.managesolution.data.dto.trainer.response.TrainerDTO;
import com.example.managesolution.data.dto.trainer.request.TrainerFormDTO;
import com.example.managesolution.mapper.AppUserMapper;
import com.example.managesolution.mapper.TrainerMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TrainerService {
    private final TrainerMapper trainerMapper;
    private final AppUserMapper appUserMapper;

    public List<TrainerDTO> getTrainerList() {
        return trainerMapper.getAllTrainer();
    }

    public void save(TrainerFormDTO trainerFormDTO) {
        AppUser appUser = AppUser.builder()
                .email(trainerFormDTO.getEmail())
                .password(trainerFormDTO.getPassword())
                .name(trainerFormDTO.getName())
                .role(trainerFormDTO.getRole())
                .createdAt(LocalDateTime.now())
                .build();

        appUserMapper.insert(appUser);
        Trainer trainer = Trainer.builder()
                .trainerId(appUser.getUserId())
                .phone(trainerFormDTO.getPhone())
                .gender(trainerFormDTO.getGender())
                .birthDate(trainerFormDTO.getBirthDate())
                .baseSalary(trainerFormDTO.getBaseSalary())
                .payPerSession(trainerFormDTO.getPayPerSession())
                .careerYears(trainerFormDTO.getCareerYears())
                .createdAt(LocalDateTime.now())
                .build();
        trainerMapper.insert(trainer);
    }


}
