package com.example.managesolution.service;

import com.example.managesolution.data.domain.Member;
import com.example.managesolution.data.domain.Membership;
import com.example.managesolution.data.domain.PtPackage;
import com.example.managesolution.data.dto.MemberFormDTO;
import com.example.managesolution.data.enumerate.Status;
import com.example.managesolution.mapper.MemberMapper;
import com.example.managesolution.mapper.MemberShipMapper;
import com.example.managesolution.mapper.PtPackageMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberMapper memberMapper;
    private final MemberShipMapper memberShipMapper;
    private final PtPackageMapper ptPackageMapper;
    private final MembershipService membershipService;
    private final PtPackageService ptPackageService;

    public List<Member> findPaged(int page, int size) {
        int offset = (page - 1) * size;
        return memberMapper.findPaged(size, offset);
    }

    public int countAll() {
        return memberMapper.countAll();
    }
    public Member findById(Long id) {
        return memberMapper.findById(id);
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
        // 2. 상품 등록
        if ("MEMBERSHIP".equals(dto.getProductType())) {
            Membership membership = Membership.builder()
                    .memberId(member.getMemberId())
                    .productId(dto.getMembershipProductId())
                    .startDate(dto.getMembershipStartDate())
                    .endDate(dto.getMembershipEndDate())
                    .price(dto.getMembershipPrice())
                    .isActive(true)
                    .createdAt(LocalDateTime.now())
                    .build();
            if (LocalDate.now().isAfter(membership.getEndDate())) {
                membership.setIsActive(false);
            }

            memberShipMapper.saveMembership(membership);

        } else if ("PT".equals(dto.getProductType())) {
            PtPackage ptPackage = PtPackage.builder()
                    .memberId(member.getMemberId())
                    .trainerId(dto.getTrainerId())
                    .productId(dto.getPtProductId())
                    .startDate(dto.getPtStartDate())
                    .endDate(dto.getPtEndDate())
                    .totalCount(dto.getPtTotalCount())
                    .remainingCount(0)
                    .price(dto.getPtPrice())
                    .createdAt(LocalDateTime.now())
                    .build();

            if (LocalDate.now().isAfter(ptPackage.getEndDate())) {
                ptPackage.setIsActive(false);
            }
            ptPackageMapper.savePtPackage(ptPackage);
        }
    }

    @Transactional
    public void update(Long id,MemberFormDTO dto) {
        Member member = Member.builder()
                .memberId(id)
                .name(dto.getName())
                .phone(dto.getPhone())
                .birthDate(dto.getBirthDate())
                .gender(dto.getGender())
                .status(dto.getStatus())
                .memo(dto.getMemo())
                .build();
        memberMapper.update(member);

        // 2. 기존 패키지 비활성화
        ptPackageMapper.deactivateByMemberId(id);       // is_active = false
        memberShipMapper.deactivateByMemberId(id);      // is_active = false

        // 2. 상품 등록
        if ("MEMBERSHIP".equals(dto.getProductType())) {
            Membership membership = Membership.builder()
                    .memberId(member.getMemberId())
                    .productId(dto.getMembershipProductId())
                    .startDate(dto.getMembershipStartDate())
                    .endDate(dto.getMembershipEndDate())
                    .price(dto.getMembershipPrice())
                    .createdAt(LocalDateTime.now())
                    .isActive(true)
                    .build();
            memberShipMapper.saveMembership(membership);

        } else if ("PT".equals(dto.getProductType())) {
            PtPackage ptPackage = PtPackage.builder()
                    .memberId(member.getMemberId())
                    .trainerId(dto.getTrainerId())
                    .productId(dto.getPtProductId())
                    .startDate(dto.getPtStartDate())
                    .endDate(dto.getPtEndDate())
                    .totalCount(dto.getPtTotalCount())
                    .remainingCount(0)
                    .price(dto.getPtPrice())
                    .createdAt(LocalDateTime.now())
                    .build();
            ptPackageMapper.savePtPackage(ptPackage);
        }
    }

    @Transactional
    public void delete(Long id) {
        memberMapper.delete(id);
    }

    public List<Member> findByNameContaining(String keyword, int page, int size) {
        int offset = (page - 1) * size;

        boolean isNumeric = keyword != null && keyword.matches("\\d+");

        if (isNumeric) {
            // 전화번호 검색
            return memberMapper.findByPhoneContaining(keyword, size, offset);
        } else {
            // 이름 검색
            return memberMapper.findByNameContaining(keyword, size, offset);
        }
    }

    public List<Member> findByStatus(String status, int page, int size) {
        int offset = (page - 1) * size;
       return memberMapper.findByStatus(status, page, offset);
    }
    public List<Member> findByStatusAndKeyword(String status, String keyword, int page, int size) {
        int offset = (page - 1) * size;

        boolean isNumeric = keyword != null && keyword.matches("\\d+");

        if (isNumeric) {
            // 전화번호 검색
            return memberMapper.findByStatusAndName(status, keyword, page, size);
        } else {
            // 이름 검색
            return memberMapper.findByStatusAndPhone(status, keyword, page, size);
        }
    }

    public void registerNewProduct(Long memberId, MemberFormDTO dto) {
        if ("MEMBERSHIP".equals(dto.getProductType())) {
            Membership membership = Membership.builder()
                    .memberId(memberId)
                    .productId(dto.getMembershipProductId())
                    .startDate(dto.getMembershipStartDate())
                    .endDate(dto.getMembershipEndDate())
                    .price(dto.getMembershipPrice())
                    .createdAt(LocalDateTime.now())
                    .isActive(true)
                    .build();
            memberShipMapper.saveMembership(membership);
        } else if ("PT".equals(dto.getProductType())) {
            PtPackage ptPackage = PtPackage.builder()
                    .memberId(memberId)
                    .trainerId(dto.getTrainerId())
                    .productId(dto.getPtProductId())
                    .startDate(dto.getPtStartDate())
                    .endDate(dto.getPtEndDate())
                    .totalCount(dto.getPtTotalCount())
                    .remainingCount(0)
                    .price(dto.getPtPrice())
                    .createdAt(LocalDateTime.now())
                    .build();
            ptPackageMapper.savePtPackage(ptPackage);
        }
    }



}
