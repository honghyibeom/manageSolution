package com.example.managesolution.mapper;

import com.example.managesolution.data.domain.Payment;
import com.example.managesolution.data.dto.MemberUnpaidDTO;
import com.example.managesolution.data.dto.PaymentDTO;
import com.example.managesolution.data.dto.PaymentHistoryDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface PaymentMapper {

    List<PaymentHistoryDTO> findPaymentHistory(@Param("keyword") String keyword);

    void insertPayment(Payment payment);

}
