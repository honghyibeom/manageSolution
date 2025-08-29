package com.example.managesolution.service;

import com.example.managesolution.data.dto.payment.response.PaymentDTO;
import com.example.managesolution.data.dto.ptSession.response.PtMemberDTO;
import com.example.managesolution.mapper.SubscriptionMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SubscriptionService {

    private final SubscriptionMapper subscriptionMapper;

//    public List<PtCareDTO> getAllCareMembers() {
//        return ptPackageMapper.getAllCareMembers();
//    }
//
//    public List<PtCareDTO> findByNamePhone(String keyword) {
//        boolean isNumeric = keyword != null && keyword.matches("\\d+");
//
//        if (isNumeric) {
//            // 전화번호 검색
//            return ptPackageMapper.findByPhoneContaining(keyword);
//        } else {
//            // 이름 검색
//            return ptPackageMapper.findByNameContaining(keyword);
//        }
//    }
//
    public List<PtMemberDTO> findByTrainerId(Long trainerId) {
        return subscriptionMapper.findByTrainerId(trainerId);
    }
//
    @Transactional
    public void updateTrainerForMembers(List<Long> memberIds, Long newTrainerId) {
        for (Long memberId : memberIds) {
            subscriptionMapper.updateTrainerByMemberId(memberId, newTrainerId);
        }
    }
//
//    public Long findPackageIdByMemberId(Long memberId) {
//        return ptPackageMapper.findPackageIdByMemberId(memberId);
//    }
//
//    public PtPackage findByMemberId(Long memberId) {
//        return ptPackageMapper.findByMemberId(memberId);
//    }
//
    @Transactional
    public void deleteUnpaid(List<PaymentDTO> unpaidList) {
        for (PaymentDTO paymentDTO : unpaidList) {
            System.out.println(paymentDTO.getSubscriptionId());
            subscriptionMapper.delete(paymentDTO.getSubscriptionId());
        }
    }

}
