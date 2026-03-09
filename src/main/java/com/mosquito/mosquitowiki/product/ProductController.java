package com.mosquito.mosquitowiki.product;

import com.mosquito.mosquitowiki.product.dto.ProductSearchResponse;
import com.mosquito.mosquitowiki.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/product")
public class ProductController {
    private final ProductService productService;

    @GetMapping("/search/{keyword}")
    public ResponseEntity<List<ProductSearchResponse>> search(@RequestParam String keyword) {
        if (keyword == null || keyword.isEmpty()) {
            return ResponseEntity.ok(List.of());
        }
        List<ProductSearchResponse> products = productService.search(keyword);
        return ResponseEntity.ok(products);
    }
}
