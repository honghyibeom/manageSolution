package com.example.managesolution.mapper;

import com.example.managesolution.data.domain.Payment;
import com.example.managesolution.data.dto.MemberUnpaidDTO;
import com.example.managesolution.data.dto.PaymentDTO;
import com.example.managesolution.data.dto.PaymentHistoryDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface PaymentMapper {

    List<PaymentHistoryDTO> findPaymentHistory();

    void insertPayment(Payment payment);

}
