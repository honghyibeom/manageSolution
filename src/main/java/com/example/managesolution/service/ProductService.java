package com.example.managesolution.service;

import com.example.managesolution.data.domain.Product;
import com.example.managesolution.mapper.ProductMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductMapper productMapper;

    public List<Product> findAll() {
        return productMapper.findAll();
    }

    @Transactional
    public void save(Product product) {
        product.setCreatedAt(LocalDateTime.now());
        productMapper.insert(product);
    }

    @Transactional
    public void update(Product product) {
        productMapper.update(product);
    }

    public Product findById(Long id) {
        return productMapper.findById(id);
    }

    @Transactional
    public void delete(Long id) {
        productMapper.delete(id);
    }

    public List<Product> getMembershipProducts() {
        return productMapper.findMembershipProducts();
    }

    public List<Product> getPtProducts() {
        return productMapper.findPtProducts();
    }


}
