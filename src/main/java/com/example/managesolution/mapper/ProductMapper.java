package com.example.managesolution.mapper;

import com.example.managesolution.data.domain.Product;
import org.apache.ibatis.annotations.Mapper;

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

}
