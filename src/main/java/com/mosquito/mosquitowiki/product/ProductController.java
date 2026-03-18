package com.mosquito.mosquitowiki.product;

import com.mosquito.mosquitowiki.product.domain.Product;
import com.mosquito.mosquitowiki.product.dto.*;
import com.mosquito.mosquitowiki.product.service.ProductService;
import com.mosquito.mosquitowiki.users.AuthUser;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/product")
public class ProductController {
    private final ProductService productService;

    @GetMapping("/search")
    public ResponseEntity<List<ProductSearchResponse>> search(@RequestParam String keyword) {
        if (keyword == null || keyword.isEmpty()) {
            return ResponseEntity.ok(List.of());
        }
        List<ProductSearchResponse> products = productService.search(keyword);
        return ResponseEntity.ok(products);
    }

    @PostMapping
    public ResponseEntity<Map<String, String>> save(
            @RequestPart("data") ProductCreateRequest request,
            @RequestPart(value = "image", required = false) List<MultipartFile> images,
            @AuthenticationPrincipal AuthUser user
    ) {
        String slug = productService.save(request, images, user.getUser());
        return ResponseEntity.ok(Map.of("slug", slug));
    }

    @GetMapping("/{slug}")
    public ResponseEntity<ProductDetailResponse> detail(
            @PathVariable String slug,
            @AuthenticationPrincipal AuthUser user
    ) {
        return ResponseEntity.ok(productService.detail(slug, user.getUser()));
    }

    @GetMapping("/info/{slug}")
    public ResponseEntity<ProductSearchResponse> info(@PathVariable String slug) {
        return ResponseEntity.ok(productService.info(slug));
    }

    @PutMapping("/{slug}")
    public ResponseEntity<ProductDetailResponse> modify(
            @PathVariable String slug,
            @RequestPart("data") ProductModifyRequest request,
            @RequestPart(value = "newImages", required = false) List<MultipartFile> images,
            @AuthenticationPrincipal AuthUser user
    ) {
        return ResponseEntity.ok(productService.modify(slug, request, images, user.getUser()));
    }

    @PostMapping("/like/{slug}")
    public ResponseEntity<LikeResponse> like(
            @PathVariable String slug,
            @AuthenticationPrincipal AuthUser user
    ) {
        return ResponseEntity.ok(productService.toggleLike(slug, user.getUser()));
    }

}
