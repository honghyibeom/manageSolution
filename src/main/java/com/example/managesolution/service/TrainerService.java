package com.example.managesolution.service;

import com.example.managesolution.data.domain.AppUser;
import com.example.managesolution.data.domain.Trainer;
import com.example.managesolution.data.dto.trainer.response.TrainerDTO;
import com.example.managesolution.data.dto.trainer.request.TrainerFormDTO;
import com.example.managesolution.mapper.AppUserMapper;
import com.example.managesolution.mapper.TrainerMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    @Transactional
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
    public TrainerFormDTO getFormDTO(Long id) {
        return trainerMapper.findTrainerById(id);
    }

    @Transactional
    public void update(Long id, TrainerFormDTO trainerFormDTO) {

        AppUser appUser = AppUser.builder()
                .userId(id)
                .email(trainerFormDTO.getEmail())
                .password(trainerFormDTO.getPassword())
                .name(trainerFormDTO.getName())
                .role(trainerFormDTO.getRole())
                .createdAt(trainerFormDTO.getCreatedAt())
                .build();
        appUserMapper.update(appUser);

        Trainer trainer = Trainer.builder()
                .trainerId(id)
                .phone(trainerFormDTO.getPhone())
                .gender(trainerFormDTO.getGender())
                .birthDate(trainerFormDTO.getBirthDate())
                .baseSalary(trainerFormDTO.getBaseSalary())
                .payPerSession(trainerFormDTO.getPayPerSession())
                .careerYears(trainerFormDTO.getCareerYears())
                .createdAt(trainerFormDTO.getCreatedAt())
                .updatedAt(LocalDateTime.now())
                .build();
        trainerMapper.update(trainer);
    }

    @Transactional
    public void delete(Long id) {
        appUserMapper.deleteById(id);
    }


}
