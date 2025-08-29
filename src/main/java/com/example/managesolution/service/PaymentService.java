package com.example.managesolution.service;

import com.example.managesolution.data.domain.Payment;
import com.example.managesolution.data.dto.payment.response.*;
import com.example.managesolution.data.dto.statistics.response.*;
import com.example.managesolution.mapper.*;
import lombok.RequiredArgsConstructor;
import org.springframework.cglib.core.Local;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PaymentService {
    private final PaymentMapper paymentMapper;
    private final MemberMapper memberMapper;
    private final SubscriptionMapper subscriptionMapper;
    private final ProductMapper productMapper;

    public List<MemberUnpaidDTO> getUnpaidMembers(String keyword, LocalDate startDate,
                                                  LocalDate endDate) {
        return memberMapper.findUnpaidMembers(keyword, startDate, endDate);
    }

    public List<MemberExpiredDTO> getExpiredMembers(String keyword, LocalDate startDate,
                                                    LocalDate endDate) {
        return memberMapper.findExpiredMembers(keyword, startDate, endDate);
    }

    public List<PaymentHistoryDTO> getPaymentHistory(String keyword,LocalDate startDate, LocalDate endDate,
                                                     int page, int size) {
        int offset = (page - 1) * size;
        LocalDateTime start = (startDate != null) ? startDate.atStartOfDay() : null;          // 00:00:00
        LocalDateTime end   = (endDate != null) ? endDate.atStartOfDay() : null;
        return paymentMapper.findPaymentHistory(keyword,start,end, size, offset);
    }

    @Transactional
    public void insertPaymentsAndActivate(List<PaymentDTO> payments) {

        for (PaymentDTO dto : payments) {
            if (dto.getMemberId() == null || dto.getProductId() == null || dto.getAmount() == null) {
                continue; // 잘못된 데이터는 건너뜀
            }

            // payment insert
            Payment payment = Payment.builder()
                    .memberId(dto.getMemberId())
                    .productId(dto.getProductId())
                    .amount(dto.getAmount())
                    .method(dto.getMethod())
                    .paidAt(LocalDateTime.now())
                    .build();

            paymentMapper.insertPayment(payment);

            // member status update
            memberMapper.updateStatusActive(dto.getMemberId());

            // 2. 구독 활성화
            subscriptionMapper.activateAndUpdatePaymentId(dto.getSubscriptionId(), payment.getPaymentId());

            // 3. 회원 활성화
            memberMapper.updateStatusActive(dto.getMemberId());

        }
    }

    public SalesDTO getSales() {
        // 총 매출
        Long totalSales = paymentMapper.findTotalSales();
        // 월 매출
        Long monthlySales = paymentMapper.findMonthlySales();
        // 일 매출
        Long dailySales = paymentMapper.findDailySales();

        return SalesDTO.builder()
                .totalSales(totalSales)
                .monthlySales(monthlySales)
                .dailySales(dailySales)
                .build();
    }

    public StatisticsResponseDTO getStatisticsData(LocalDate startDate, LocalDate endDate, String type) {
        // 🔷 차트 데이터 조회
        List<LabelAndAmountDTO> chartData = paymentMapper.selectChartData(startDate, endDate, type);

        List<String> labels = new ArrayList<>();
        List<Long> salesData = new ArrayList<>();
        for (LabelAndAmountDTO dto : chartData) {
            labels.add(dto.getLabel());
            salesData.add(dto.getAmount());
        }

        // 🔷 PT권 테이블 데이터 조회
        List<PtMemberSalesDTO> ptMembers = productMapper.selectPtMembers(startDate, endDate);

        // 🔷 회원권 테이블 데이터 조회
        List<MembershipSalesDTO> membershipMembers = productMapper.selectMembershipMembers(startDate, endDate);

        // 🔷 PT권 합계 계산
        long ptTotalAmount = ptMembers.stream()
                .mapToLong(PtMemberSalesDTO::getTotalAmount)
                .sum();

        int ptCount = 0;
        for (PtMemberSalesDTO ptMember : ptMembers) {
            ptCount = ptCount + ptMember.getCount();
        }


        SummaryDTO ptSummary = SummaryDTO.builder()
                .totalAmount(ptTotalAmount)
                .count(ptCount)
                .build();

        // 🔷 회원권 합계 계산
        long membershipTotalAmount = membershipMembers.stream()
                .mapToLong(MembershipSalesDTO::getTotalAmount)
                .sum();

        int membershipCount = 0;
        for (MembershipSalesDTO membership : membershipMembers) {
            membershipCount = membershipCount + membership.getCount();
        }

        SummaryDTO membershipSummary = SummaryDTO.builder()
                .totalAmount(membershipTotalAmount)
                .count(membershipCount)
                .build();

        return StatisticsResponseDTO.builder()
                .labels(labels)
                .salesData(salesData)
                .membershipMembers(membershipMembers)
                .ptMembers(ptMembers)
                .membershipSummary(membershipSummary)
                .ptSummary(ptSummary)
                .build();
    }
    public int countAll(String keyword, LocalDate startDate, LocalDate endDate) {
        return paymentMapper.countAll(keyword,startDate, endDate);
    }

    public List<DetailStatisticsDTO> findDetails(String category, String name, LocalDate start, LocalDate end) {
        return paymentMapper.findStatisticsDetails(category, name, start, end);
    }
}
