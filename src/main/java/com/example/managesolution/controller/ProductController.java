package com.example.managesolution.controller;

import com.example.managesolution.data.domain.Product;
import com.example.managesolution.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    // 상품 목록 페이지
    @GetMapping
    public String index(@RequestParam(defaultValue = "ALL") String type, Model model) {

        List<Product> products = switch (type.toUpperCase()) {
            case "MEMBERSHIP" -> productService.getMembershipProducts();
            case "PT" -> productService.getPtProducts();
            default -> productService.findAll();
        };

        model.addAttribute("products", products);
        model.addAttribute("type", type); // 어떤 버튼이 선택됐는지 판단용
        model.addAttribute("product", Product.builder().isActive(true).build()); // 모달 초기값
        return "product/list";
    }

    /**
     * 상품 등록/수정 처리 (productId 유무로 구분)
     */
    @PostMapping
    public String saveOrUpdate(@ModelAttribute("product") Product product) {
        if (product.getProductId() == null) {
            productService.save(product); // 신규 등록
        } else {
            productService.update(product); // 수정
        }
        return "redirect:/products";
    }

    /**
     * 상품 삭제 처리
     */
    @PostMapping("/{id}/delete")
    public String delete(@PathVariable Long id) {
        productService.delete(id);
        return "redirect:/products";
    }
//---------------------------------------------RestAPI-----------------------------------------------------------//

    // 상품 수정시 id 검색
    @GetMapping("/{id}/json")
    @ResponseBody
    public Product getProductJson(@PathVariable Long id) {
        return productService.findById(id);
    }
}
