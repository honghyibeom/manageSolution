package com.example.managesolution.mapper;

import com.example.managesolution.data.domain.Subscription;
import com.example.managesolution.data.dto.care.response.ImminentCareDTO;
import com.example.managesolution.data.dto.care.response.PtCareDTO;
import com.example.managesolution.data.dto.ptSession.response.PtMemberDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SubscriptionMapper {
    // insert
    void insert(Subscription subscription);
    // 최신구독 조회
    Subscription findLatestByMemberId(Long memberId);
    // 결제 전 구독 수정
    void update(Subscription subscription);
    // 결제 후 active 활성화
    void activateAndUpdatePaymentId(Long subscriptionId, Long paymentId);
    // 트레이너 별 회원 조회
    List<PtMemberDTO> findByTrainerId(Long trainerId);
    // 회원 트레이너 변환
    void updateTrainerByMemberId(Long memberId, Long trainerId);
    // 구독권 삭제
    void delete(Long subscriptionId);
    // 구독 id로 찾기
    Subscription findBySubscriptionId(Long subscriptionId);
    // 구독 수정
    void updateSubscription(Subscription subscription);
    // pt 횟수 증가
    void increaseRemainingCount(Long subscriptionId);
    // 첫 pt 인원 조회
    List<PtCareDTO> findFirstPtByPtMembers(String keyword);
    // 마감 임박 회원 조회
    List<ImminentCareDTO> findImminentByMemberships(String keyword);

}
