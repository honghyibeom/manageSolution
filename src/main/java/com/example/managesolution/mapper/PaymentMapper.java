package com.example.managesolution.mapper;

import com.example.managesolution.data.domain.Payment;
import com.example.managesolution.data.dto.payment.response.LabelAndAmountDTO;
import com.example.managesolution.data.dto.payment.response.PaymentHistoryDTO;
import com.example.managesolution.data.dto.statistics.response.DetailStatisticsDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDate;
import java.util.List;

@Mapper
public interface PaymentMapper {

    List<PaymentHistoryDTO> findPaymentHistory(@Param("keyword") String keyword,
                                               @Param("limit")int limit,
                                               @Param("offset")int offset);

    void insertPayment(Payment payment);

    Long findTotalSales();
    Long findMonthlySales();
    Long findDailySales();

    List<LabelAndAmountDTO> selectChartData(
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate,
            @Param("type") String type
    );

    int countAll(@Param("keyword") String keyword);

    List<DetailStatisticsDTO> findStatisticsDetails(String category, String name, LocalDate startDate, LocalDate endDate);
}