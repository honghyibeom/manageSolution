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
    // 모든 상품 조회
    List<Product> findAll();
    // 상품 추가
    void insert(Product product);
    // 상품 수정
    void update(Product product);
    // 상품 삭제
    void delete(Long id);
    // 상품 id로 조회
    Product findById(Long productId);
    // 회원권 상품 조회
    List<Product> findMembershipProducts();
    // PT권 상품 조회
    List<Product> findPtProducts();
    // 상품 랭킹 조회
    List<ProductRankingDTO> findProductRankings();
    // pt 통계 조회
    List<MembershipSalesDTO> selectMembershipMembers(@Param("startDate") LocalDate startDate,
                                                     @Param("endDate") LocalDate endDate);
    // 회원권 통계 조회
    List<PtMemberSalesDTO> selectPtMembers(@Param("startDate") LocalDate startDate,
                                           @Param("endDate") LocalDate endDate);

}
