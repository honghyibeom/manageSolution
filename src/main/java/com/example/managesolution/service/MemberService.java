package com.example.managesolution.service;

import com.example.managesolution.data.domain.Member;
import com.example.managesolution.data.domain.Subscription;
import com.example.managesolution.data.dto.member.request.MemberFormDTO;
import com.example.managesolution.data.dto.member.response.MemberProductDTO;
import com.example.managesolution.data.enumerate.Status;
import com.example.managesolution.exception.CustomException;
import com.example.managesolution.exception.ErrorCode;
import com.example.managesolution.mapper.MemberMapper;
import com.example.managesolution.mapper.SubscriptionMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberMapper memberMapper;
    private final SubscriptionMapper subscriptionMapper;

    public List<MemberProductDTO> findMembers(String status, String keyword, int page, int size) {
        int offset = (page - 1) * size;
        boolean isNumeric = keyword != null && keyword.matches("\\d+");
        return memberMapper.findMembers(status, keyword, size, offset, isNumeric);
    }

    public int countAll() {
        return memberMapper.countAll();
    }
    public Member findById(Long id) {
        Member member = memberMapper.findById(id);
        if (member == null) {
            throw new CustomException(ErrorCode.NOT_EXIST_USER);
        }
        return member;
    }

    // 회원등록 로직
    @Transactional
    public void save(MemberFormDTO dto) {
        Member member = Member.builder()
                .name(dto.getName())
                .phone(dto.getPhone())
                .birthDate(dto.getBirthDate())
                .gender(dto.getGender())
                .status(Status.INACTIVE)
                .memo(dto.getMemo())
        .build();
        memberMapper.insert(member);

        Subscription subscription = Subscription.builder()
                .memberId(member.getMemberId())
                .productId(dto.getProductId())
                .productType(dto.getProductType())
                .price(dto.getPrice())
                .remainingCount(dto.getRemainingCount())
                .remainingCount(dto.getRemainingCount())
                .startDate(dto.getStartDate())
                .endDate(dto.getEndDate())
                .totalCount(dto.getTotalCount())
                .trainerId(dto.getTrainerId())
                .createdAt(LocalDateTime.now())
                .isActive(true)
                .build();
        subscriptionMapper.insert(subscription);
    }

    @Transactional
    public void update(Long memberId, MemberFormDTO dto) {
        // 1) 회원 정보는 항상 수정
        Member member = Member.builder()
                .memberId(memberId)
                .name(dto.getName())
                .phone(dto.getPhone())
                .birthDate(dto.getBirthDate())
                .gender(dto.getGender())
                .status(dto.getStatus())
                .memo(dto.getMemo())
                .build();
        memberMapper.update(member);

        // 2) 상품 관련 처리
        Subscription latest = subscriptionMapper.findLatestByMemberId(memberId);

        if (dto.getProductType() == null || dto.getProductType().isBlank()) {
            // 상품 입력 자체가 없으면 무시
            return;
        }

        // 상품이 없었을 경우 새로 등록
        if (latest == null) {
            subscriptionMapper.insert(toSubscription(dto, memberId, null));
            return;
        }

        // 🚩 기존 subscription과 비교
        boolean changed = !latest.getProductId().equals(dto.getProductId()) ||
                !latest.getProductType().equals(dto.getProductType()) ||
                !latest.getPrice().equals(dto.getPrice())||
                !latest.getStartDate().equals(dto.getStartDate())||
                !latest.getEndDate().equals(dto.getEndDate());

        if (changed) {
            if (latest.getPaymentId() == null) {
                subscriptionMapper.update(toSubscription(dto, memberId, latest.getSubscriptionId()));
            } else {
                throw new CustomException(ErrorCode.EXIST_PAYMENT);
            }
        }
    }

    @Transactional
    public void delete(Long id) {
        memberMapper.delete(id);
    }

    @Transactional
    public void registerNewProduct(Long memberId, MemberFormDTO dto) {
        subscriptionMapper.insert(toSubscription(dto, memberId, null));
    }

    public MemberFormDTO toFormDTO(Long memberId) {
        return memberMapper.findFormById(memberId);
    }

    public MemberFormDTO toBasicFormDTO(Long memberId) {
        Member member = findById(memberId);

        MemberFormDTO dto = new MemberFormDTO();
        dto.setMemberId(member.getMemberId());
        dto.setName(member.getName());
        dto.setPhone(member.getPhone());
        dto.setBirthDate(member.getBirthDate());
        dto.setGender(member.getGender());
        dto.setMemo(member.getMemo());
        dto.setStatus(member.getStatus());

        return dto;
    }

    private Subscription toSubscription(MemberFormDTO dto, Long memberId, Long subscriptionId) {
        return Subscription.builder()
                .subscriptionId(subscriptionId) // update 시에만 값 들어감
                .memberId(memberId)
                .productId(dto.getProductId())
                .productType(dto.getProductType())
                .trainerId(dto.getTrainerId())
                .startDate(dto.getStartDate())
                .endDate(dto.getEndDate())
                .totalCount(dto.getTotalCount())
                .remainingCount(dto.getRemainingCount())
                .price(dto.getPrice())
                .isActive(true)
                .build();
    }
}
