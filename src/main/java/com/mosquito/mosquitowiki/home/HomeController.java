package com.mosquito.mosquitowiki.home;

import com.mosquito.mosquitowiki.product.service.BrandService;
import com.mosquito.mosquitowiki.product.service.ProductService;
import com.mosquito.mosquitowiki.swatch.SwatchService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
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
}
