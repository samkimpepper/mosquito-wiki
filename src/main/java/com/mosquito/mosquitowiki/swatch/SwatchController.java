package com.mosquito.mosquitowiki.swatch;

import com.mosquito.mosquitowiki.product.dto.LikeResponse;
import com.mosquito.mosquitowiki.swatch.dto.SwatchCreateRequest;
import com.mosquito.mosquitowiki.swatch.dto.SwatchDetailResponse;
import com.mosquito.mosquitowiki.users.AuthUser;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/swatch")
public class SwatchController {
    private final SwatchService swatchService;

    @PostMapping
    public ResponseEntity<Long> create(
            @RequestPart("data")SwatchCreateRequest request,
            @RequestPart(value = "images", required = false) List<MultipartFile> images,
            @AuthenticationPrincipal AuthUser user
    ) {
        return ResponseEntity.ok(swatchService.create(request, user.getUser(), images));
    }

    @GetMapping
    public ResponseEntity<Page<SwatchDetailResponse>> details(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String productSlug,
            @AuthenticationPrincipal AuthUser user
    ) {
        return ResponseEntity.ok(swatchService.detail(page, size, productSlug, user.getUser()));
    }

    @PostMapping("/like/{id}")
    public ResponseEntity<LikeResponse> like(
            @PathVariable Long id,
            @AuthenticationPrincipal AuthUser user
    ) {
        return ResponseEntity.ok(swatchService.toggleLike(id, user.getUser()));
    }
}
