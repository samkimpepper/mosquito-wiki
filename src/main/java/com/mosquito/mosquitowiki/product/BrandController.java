package com.mosquito.mosquitowiki.product;

import com.mosquito.mosquitowiki.product.dto.BrandCreateRequest;
import com.mosquito.mosquitowiki.product.dto.BrandDetailResponse;
import com.mosquito.mosquitowiki.product.dto.BrandSearchResponse;
import com.mosquito.mosquitowiki.product.service.BrandService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/brand")
@Slf4j
public class BrandController {
    private final BrandService brandService;

    @PostMapping
    public ResponseEntity<Map<String, String>> save(
            @RequestPart("data") BrandCreateRequest request,
            @RequestPart(value = "image", required = false) MultipartFile image
    ) {
        String slug = brandService.save(request, image);
        return ResponseEntity.ok(Map.of("slug", slug));
    }

    @GetMapping("/search")
    public ResponseEntity<List<BrandSearchResponse>> search(@RequestParam String keyword) {
        if (keyword == null || keyword.isEmpty()) {
            return ResponseEntity.ok(List.of());
        }

        return ResponseEntity.ok(brandService.search(keyword));
    }

    @GetMapping("/info/{slug}")
    public ResponseEntity<BrandSearchResponse> info(@PathVariable String slug) {
        return ResponseEntity.ok(brandService.info(slug));
    }

    @GetMapping("/{slug}")
    public ResponseEntity<BrandDetailResponse> detail(@PathVariable String slug) {
        return ResponseEntity.ok(brandService.showDetail(slug));
    }
}
