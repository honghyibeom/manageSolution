package com.example.managesolution.mapper;

import com.example.managesolution.data.domain.Payment;
import com.example.managesolution.data.dto.payment.response.LabelAndAmountDTO;
import com.example.managesolution.data.dto.payment.response.PaymentHistoryDTO;
import com.example.managesolution.data.dto.statistics.response.DetailStatisticsDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Mapper
public interface PaymentMapper {
    // 결제 내역
    List<PaymentHistoryDTO> findPaymentHistory(@Param("keyword") String keyword,
                                               @Param("startDate") LocalDateTime startDate,
                                               @Param("endDate") LocalDateTime endDate,
                                               @Param("limit")int limit,
                                               @Param("offset")int offset);
    // 결제 내역 페이징
    int countAll(@Param("keyword") String keyword,
                 @Param("startDate") LocalDate startDate,
                 @Param("endDate") LocalDate endDate
    );
    // 결제 내역 추가
    void insertPayment(Payment payment);
    // 총매출
    Long findTotalSales();
    // 월매출
    Long findMonthlySales();
    // 일매출
    Long findDailySales();
    // 차트 데이터 조회
    List<LabelAndAmountDTO> selectChartData(
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate,
            @Param("type") String type
    );
    // 결제 통계 디테일 조회
    List<DetailStatisticsDTO> findStatisticsDetails(String category, String name, LocalDate startDate, LocalDate endDate);
}