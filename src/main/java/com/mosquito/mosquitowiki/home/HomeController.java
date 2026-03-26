package com.mosquito.mosquitowiki.home;

import com.mosquito.mosquitowiki.product.dto.PopularProductResponse;
import com.mosquito.mosquitowiki.product.service.BrandService;
import com.mosquito.mosquitowiki.product.service.ProductService;
import com.mosquito.mosquitowiki.swatch.Swatch;
import com.mosquito.mosquitowiki.swatch.SwatchService;
import com.mosquito.mosquitowiki.swatch.dto.PopularSwatchResponse;
import com.mosquito.mosquitowiki.users.AuthUser;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/home")
public class HomeController {
    private final BrandService brandService;
    private final ProductService productService;
    private final SwatchService swatchService;

    @GetMapping("/dashboard")
    public ResponseEntity<DashBoardResponse> dashBoard() {
        DashBoardResponse response = DashBoardResponse.builder()
                .brandCount(brandService.count())
                .productCount(productService.count())
                .swatchCount(swatchService.count())
                .categoryStats(productService.categoryStat())
                .build();

        return ResponseEntity.ok(response);
    }

    @GetMapping("/popular-products")
    public ResponseEntity<Page<PopularProductResponse>> popularProducts(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String categorySlug
    ) {
        return ResponseEntity.ok(productService.popularProducts(page, size, categorySlug));
    }

    @GetMapping("/popular-swatches")
    public ResponseEntity<Page<PopularSwatchResponse>> popularSwatches(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @AuthenticationPrincipal AuthUser user
            ) {
        return ResponseEntity.ok(swatchService.popularSwatches(page, size, user.getUser()));
    }
}
