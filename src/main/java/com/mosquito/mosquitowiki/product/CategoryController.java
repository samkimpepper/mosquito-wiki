package com.mosquito.mosquitowiki.product;

import com.mosquito.mosquitowiki.product.dto.CategoryResponse;
import com.mosquito.mosquitowiki.product.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/category")
public class CategoryController {
    private final CategoryRepository categoryRepository;

    @GetMapping
    public ResponseEntity<List<CategoryResponse>> showAllCategories() {
        return ResponseEntity.ok(categoryRepository.findAll().stream().map(CategoryResponse::from).toList());
    }
}
