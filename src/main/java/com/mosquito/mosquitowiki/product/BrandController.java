package com.mosquito.mosquitowiki.product;

import com.mosquito.mosquitowiki.product.dto.BrandCreateRequest;
import com.mosquito.mosquitowiki.product.dto.BrandSearchResponse;
import com.mosquito.mosquitowiki.product.service.BrandService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/brand")
public class BrandController {
    private final BrandService brandService;

    @PostMapping
    public ResponseEntity<Map<String, Long>> save(@RequestBody BrandCreateRequest request) {
        Long id = brandService.save(request);
        return ResponseEntity.ok(Map.of("id", id));
    }

    @GetMapping("/search/{keyword}")
    public ResponseEntity<List<BrandSearchResponse>> search(@RequestParam String keyword) {
        if (keyword == null || keyword.isEmpty()) {
            return ResponseEntity.ok(List.of());
        }

        return ResponseEntity.ok(brandService.search(keyword));
    }
}
