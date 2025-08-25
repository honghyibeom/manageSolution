package com.example.managesolution.service;

import com.example.managesolution.data.domain.Member;
import com.example.managesolution.data.domain.Membership;
import com.example.managesolution.data.domain.PtPackage;
import com.example.managesolution.data.dto.member.request.MemberFormDTO;
import com.example.managesolution.data.dto.member.response.MemberProductDTO;
import com.example.managesolution.data.enumerate.Status;
import com.example.managesolution.exception.CustomException;
import com.example.managesolution.exception.ErrorCode;
import com.example.managesolution.mapper.MemberMapper;
import com.example.managesolution.mapper.MemberShipMapper;
import com.example.managesolution.mapper.PtPackageMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    public List<MemberProductDTO> findMembers(String status, String keyword, int page, int size) {
        int offset = (page - 1) * size;

        boolean isNumeric = keyword != null && keyword.matches("\\d+");

        if (status != null && !status.isBlank() && keyword != null && !keyword.isBlank()) {
            // 상태 + 키워드
            if (isNumeric) {
                return memberMapper.findByStatusAndPhone(status, keyword, size, offset);
            } else {
                return memberMapper.findByStatusAndName(status, keyword, size, offset);
            }
        } else if (status != null && !status.isBlank()) {
            // 상태만
            return memberMapper.findByStatus(status, size, offset);
        } else if (keyword != null && !keyword.isBlank()) {
            // 키워드만
            if (isNumeric) {
                return memberMapper.findByPhoneContaining(keyword, size, offset);
            } else {
                return memberMapper.findByNameContaining(keyword, size, offset);
            }
        } else {
            // 둘 다 없음
            return memberMapper.findPaged(size, offset);
        }
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
        // 2. 상품 등록
        if ("MEMBERSHIP".equals(dto.getProductType())) {
            Membership membership = Membership.builder()
                    .memberId(member.getMemberId())
                    .productId(dto.getMembershipProductId())
                    .startDate(dto.getMembershipStartDate())
                    .endDate(dto.getMembershipEndDate())
                    .price(dto.getMembershipPrice())
                    .isActive(false)
                    .createdAt(LocalDateTime.now())
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
                    .remainingCount(dto.getPtTotalCount())
                    .price(dto.getPtPrice())
                    .isActive(false)
                    .createdAt(LocalDateTime.now())
                    .build();
            ptPackageMapper.savePtPackage(ptPackage);
        }
    }

    @Transactional
    public void update(Long id,MemberFormDTO dto) {

        Membership existingMembership = memberShipMapper.findByMemberId(id);
        PtPackage existingPtPackage = ptPackageMapper.findByMemberId(id);


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

        // 2. 상품 등록
        if ("MEMBERSHIP".equals(dto.getProductType())) {
            Membership membership = Membership.builder()
                    .memberId(member.getMemberId())
                    .productId(dto.getMembershipProductId())
                    .startDate(dto.getMembershipStartDate())
                    .endDate(dto.getMembershipEndDate())
                    .price(dto.getMembershipPrice())
                    .createdAt(LocalDateTime.now())
                    .build();
            if (existingMembership == null) {
                memberShipMapper.saveMembership(membership);
            }
            else {
                memberShipMapper.updateMembership(membership);
            }

        } else if ("PT".equals(dto.getProductType())) {
            PtPackage ptPackage = PtPackage.builder()
                    .memberId(member.getMemberId())
                    .trainerId(dto.getTrainerId())
                    .productId(dto.getPtProductId())
                    .startDate(dto.getPtStartDate())
                    .endDate(dto.getPtEndDate())
                    .totalCount(dto.getPtTotalCount())
                    .remainingCount(dto.getPtTotalCount())
                    .price(dto.getPtPrice())
                    .createdAt(LocalDateTime.now())
                    .build();
            if (existingPtPackage == null) {
                ptPackageMapper.savePtPackage(ptPackage);
            }
            ptPackageMapper.updatePtPackage(ptPackage);
        }
    }

    @Transactional
    public void delete(Long id) {
        memberMapper.delete(id);
    }

    @Transactional
    public void registerNewProduct(Long memberId, MemberFormDTO dto) {
        if ("MEMBERSHIP".equals(dto.getProductType())) {
            Membership membership = Membership.builder()
                    .memberId(memberId)
                    .productId(dto.getMembershipProductId())
                    .startDate(dto.getMembershipStartDate())
                    .endDate(dto.getMembershipEndDate())
                    .price(dto.getMembershipPrice())
                    .createdAt(LocalDateTime.now())
                    .isActive(false)
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
                    .remainingCount(dto.getPtTotalCount())
                    .price(dto.getPtPrice())
                    .createdAt(LocalDateTime.now())
                    .isActive(false)
                    .build();
            ptPackageMapper.savePtPackage(ptPackage);
        }
    }

    @Transactional
    public MemberFormDTO toFormDTO(Long memberId) {
        Member member = findById(memberId);
        Membership membership = membershipService.findByMemberId(memberId);
        PtPackage ptPackage = ptPackageService.findByMemberId(memberId);

        MemberFormDTO dto = new MemberFormDTO();

        dto.setMemberId(member.getMemberId());
        dto.setName(member.getName());
        dto.setPhone(member.getPhone());
        dto.setBirthDate(member.getBirthDate());
        dto.setGender(member.getGender());
        dto.setMemo(member.getMemo());

        if (membership != null) {
            dto.setProductType("MEMBERSHIP");
            dto.setMembershipProductId(membership.getProductId());
            dto.setMembershipStartDate(membership.getStartDate());
            dto.setMembershipEndDate(membership.getEndDate());
            dto.setMembershipPrice(membership.getPrice());
        } else if (ptPackage != null) {
            dto.setProductType("PT");
            dto.setPtProductId(ptPackage.getProductId());
            dto.setTrainerId(ptPackage.getTrainerId());
            dto.setPtStartDate(ptPackage.getStartDate());
            dto.setPtEndDate(ptPackage.getEndDate());
            dto.setPtTotalCount(ptPackage.getTotalCount());
            dto.setPtPrice(ptPackage.getPrice());
        }

        return dto;
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
}
