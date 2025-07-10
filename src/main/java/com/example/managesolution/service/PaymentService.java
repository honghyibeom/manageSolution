package com.example.managesolution.service;

import com.example.managesolution.data.domain.Payment;
import com.example.managesolution.data.dto.*;
import com.example.managesolution.mapper.MemberMapper;
import com.example.managesolution.mapper.MemberShipMapper;
import com.example.managesolution.mapper.PaymentMapper;
import com.example.managesolution.mapper.PtPackageMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PaymentService {
private final PaymentMapper paymentMapper;
private final MemberMapper memberMapper;
        private final MemberShipMapper memberShipMapper;
        private final PtPackageMapper ptPackageMapper;

        public List<MemberUnpaidDTO> getUnpaidMembers(String keyword) {
                return memberMapper.findUnpaidMembers(keyword);
        }

        public List<MemberExpiredDTO> getExpiredMembers(String keyword) {
                return memberMapper.findExpiredMembers(keyword);
        }

        public List<PaymentHistoryDTO> getPaymentHistory(String keyword) {
                return paymentMapper.findPaymentHistory(keyword);
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

                        // membership or ptpackage update
                        if ("MEMBERSHIP".equalsIgnoreCase(dto.getProductType())) {
                                memberShipMapper.updatePaymentId(dto.getMemberId(), payment.getPaymentId());
                        } else if ("PT".equalsIgnoreCase(dto.getProductType())) {
                                ptPackageMapper.updatePaymentId(dto.getMemberId(), payment.getPaymentId());
                        }
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
                        System.out.println(dto.getAmount());
                        System.out.println(dto.getLabel());
                }

                // 🔷 PT권 테이블 데이터 조회
                List<PtMemberSalesDTO> ptMembers = ptPackageMapper.selectPtMembers(startDate, endDate);

                // 🔷 회원권 테이블 데이터 조회
                List<MembershipSalesDTO> membershipMembers = memberShipMapper.selectMembershipMembers(startDate, endDate);

                // 🔷 PT권 합계 계산
                long ptTotalAmount = ptMembers.stream()
                        .mapToLong(PtMemberSalesDTO::getPrice)
                        .sum();
                int ptCount = ptMembers.size();

                SummaryDTO ptSummary = SummaryDTO.builder()
                        .totalAmount(ptTotalAmount)
                        .count(ptCount)
                        .build();

                // 🔷 회원권 합계 계산
                long membershipTotalAmount = membershipMembers.stream()
                        .mapToLong(MembershipSalesDTO::getPrice)
                        .sum();
                int membershipCount = membershipMembers.size();

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
}
