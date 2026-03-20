package com.mosquito.mosquitowiki.swatch;

import com.mosquito.mosquitowiki.exception.BaseException;
import com.mosquito.mosquitowiki.exception.ErrorCode;
import com.mosquito.mosquitowiki.file.FileService;
import com.mosquito.mosquitowiki.product.domain.Product;
import com.mosquito.mosquitowiki.product.repository.ProductRepository;
import com.mosquito.mosquitowiki.swatch.dto.SwatchCreateRequest;
import com.mosquito.mosquitowiki.swatch.dto.SwatchDetailResponse;
import com.mosquito.mosquitowiki.swatch.repository.SwatchLinkRepository;
import com.mosquito.mosquitowiki.swatch.repository.SwatchRepository;
import com.mosquito.mosquitowiki.users.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SwatchService {
    private final SwatchRepository swatchRepository;
    private final ProductRepository productRepository;
    private final SwatchLinkRepository swatchLinkRepository;
    private final FileService fileService;

    public Long create(SwatchCreateRequest request, User user, List<MultipartFile> images) {
        List<Product> products = productRepository.findBySlugIn(request.getProductSlugs());

        if (products.size() != request.getProductSlugs().size()) {
            throw new BaseException(ErrorCode.PRODUCT_NOT_FOUND);
        }

        Map<String, Product> productMap = products.stream()
                .collect(Collectors.toMap(Product::getSlug, p -> p));

        List<String> imageUrls = new ArrayList<>();
        if (images != null && images.size() > 4) {
            throw new BaseException(ErrorCode.TOO_MANY_IMAGES);
        }
        if (images!= null && !images.isEmpty()) {
            try {
                for (MultipartFile file : images) {
                    imageUrls.add(fileService.save(file));
                }
            } catch (IOException e) {
                throw new BaseException(ErrorCode.FILE_UPLOAD_ERROR);
            }
        }

        Swatch swatch = Swatch.create(request, imageUrls, user);
        swatchRepository.save(swatch);

        for (int i = 0; i < request.getProductSlugs().size(); i++) {
            Product product = productMap.get(request.getProductSlugs().get(i));
            SwatchLink link = SwatchLink.builder()
                    .swatch(swatch)
                    .product(product)
                    .displayOrder(i)
                    .build();
            swatchLinkRepository.save(link);
        }
        return swatch.getId();
    }

    @Transactional(readOnly = true)
    public Page<SwatchDetailResponse> detail(int page, int size, String productSlug) {
        Product product = productRepository.findBySlug(productSlug).orElseThrow(() -> new BaseException(ErrorCode.PRODUCT_NOT_FOUND));
        Page<Swatch> swatches = swatchRepository.findBySwatchLinks_ProductOrderByCreatedAtDesc(product, PageRequest.of(page, size));

        return swatches.map(SwatchDetailResponse::from);
    }

    @Transactional(readOnly = true)
    public long count() {
        return swatchRepository.count();
    }
}
