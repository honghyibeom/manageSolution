package com.example.managesolution.service;

import com.example.managesolution.data.domain.Payment;
import com.example.managesolution.data.dto.MemberExpiredDTO;
import com.example.managesolution.data.dto.MemberUnpaidDTO;
import com.example.managesolution.data.dto.PaymentDTO;
import com.example.managesolution.data.dto.PaymentHistoryDTO;
import com.example.managesolution.mapper.MemberMapper;
import com.example.managesolution.mapper.MemberShipMapper;
import com.example.managesolution.mapper.PaymentMapper;
import com.example.managesolution.mapper.PtPackageMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
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
}
