package com.example.managesolution.mapper;

import com.example.managesolution.data.domain.Product;
import com.example.managesolution.data.dto.dashboard.response.ProductRankingDTO;
import com.example.managesolution.data.dto.statistics.response.MembershipSalesDTO;
import com.example.managesolution.data.dto.statistics.response.PtMemberSalesDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDate;
import java.util.List;

@Mapper
public interface ProductMapper {
    List<Product> findAll();

    void insert(Product product);

    void update(Product product);

    void delete(Long id);

    Product findById(Long productId);

    List<Product> findMembershipProducts();

    List<Product> findPtProducts();

    List<ProductRankingDTO> findProductRankings();
    // pt 통계 조회
    List<MembershipSalesDTO> selectMembershipMembers(@Param("startDate") LocalDate startDate,
                                                     @Param("endDate") LocalDate endDate);
    // 회원권 통계 조회
    List<PtMemberSalesDTO> selectPtMembers(@Param("startDate") LocalDate startDate,
                                           @Param("endDate") LocalDate endDate);

}
