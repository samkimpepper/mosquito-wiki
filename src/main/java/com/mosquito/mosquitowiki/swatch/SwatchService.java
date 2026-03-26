package com.mosquito.mosquitowiki.swatch;

import com.mosquito.mosquitowiki.exception.BaseException;
import com.mosquito.mosquitowiki.exception.ErrorCode;
import com.mosquito.mosquitowiki.file.FileService;
import com.mosquito.mosquitowiki.product.domain.Product;
import com.mosquito.mosquitowiki.product.dto.LikeResponse;
import com.mosquito.mosquitowiki.product.repository.ProductRepository;
import com.mosquito.mosquitowiki.swatch.dto.PopularSwatchResponse;
import com.mosquito.mosquitowiki.swatch.dto.SwatchCreateRequest;
import com.mosquito.mosquitowiki.swatch.dto.SwatchDetailResponse;
import com.mosquito.mosquitowiki.swatch.repository.SwatchLikeRepository;
import com.mosquito.mosquitowiki.swatch.repository.SwatchLinkRepository;
import com.mosquito.mosquitowiki.swatch.repository.SwatchRepository;
import com.mosquito.mosquitowiki.users.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class SwatchService {
    private final SwatchRepository swatchRepository;
    private final ProductRepository productRepository;
    private final SwatchLinkRepository swatchLinkRepository;
    private final SwatchLikeRepository swatchLikeRepository;
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
    public Page<SwatchDetailResponse> detail(int page, int size, String productSlug, User user) {
        log.info("productSlug: " + productSlug);
        Page<Swatch> swatches = swatchRepository.findByProductSlug(productSlug, PageRequest.of(page, size));

        Set<Long> likeIds = Set.of();
        if (user != null) {
            likeIds = swatchLikeRepository.findByUserAndSwatchIn(user, swatches.stream().toList())
                    .stream()
                    .map(sl -> sl.getSwatch().getId())
                    .collect(Collectors.toSet());
        }

        Set<Long> finalLikeIds = likeIds;

        return swatches.map(s -> SwatchDetailResponse.from(s, finalLikeIds.contains(s.getId())));
    }

    @Transactional(readOnly = true)
    public long count() {
        return swatchRepository.count();
    }

    @Transactional
    public LikeResponse toggleLike(Long id, User user) {
        Swatch swatch = swatchRepository.findById(id).orElseThrow(() -> new BaseException(ErrorCode.SWATCH_NOT_FOUND));

        boolean liked = swatchLikeRepository.existsBySwatchAndUser(swatch, user);

        if (liked) {
            swatchLikeRepository.deleteBySwatchAndUser(swatch, user);
        } else {
            swatchLikeRepository.save(SwatchLike.create(user, swatch));
        }

        return LikeResponse.builder()
                .liked(!liked)
                .likeCount(swatchLikeRepository.countBySwatch(swatch))
                .build();
    }

    @Transactional(readOnly = true)
    public Page<PopularSwatchResponse> popularSwatches(int page, int size, User user) {
        Page<Swatch> swatches = swatchRepository.findPopularSwatches(user, PageRequest.of(page, size));

        List<Swatch> swatchList = swatches.stream().toList();

        Map<Long, List<SwatchLink>> linkMap = swatchLinkRepository.findBySwatchIn(swatchList)
                .stream()
                .collect(Collectors.groupingBy(sl -> sl.getSwatch().getId()));


        Set<Long> likedIds = Set.of();
        if (user != null) {
            likedIds = swatchLikeRepository.findByUserAndSwatchIn(user, swatchList)
                    .stream()
                    .map(sl -> sl.getSwatch().getId())
                    .collect(Collectors.toSet());
        }

        Set<Long> finalLikeIds = likedIds;
        return swatches.map(s -> PopularSwatchResponse.of(
                s,
                linkMap.getOrDefault(s.getId(), List.of()),
                finalLikeIds.contains(s.getId())
        ));
    }
}
