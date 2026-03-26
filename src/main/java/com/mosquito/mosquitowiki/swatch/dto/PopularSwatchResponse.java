package com.mosquito.mosquitowiki.swatch.dto;

import com.mosquito.mosquitowiki.product.dto.ProductSearchResponse;
import com.mosquito.mosquitowiki.swatch.SourceType;
import com.mosquito.mosquitowiki.swatch.Swatch;
import com.mosquito.mosquitowiki.swatch.SwatchLink;
import com.mosquito.mosquitowiki.users.UserThumbnailResponse;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
public class PopularSwatchResponse {
    private Long id;
    private String title;
    private String content;
    private LocalDateTime createdAt;
    private List<String> imageUrls;
    private boolean liked;
    private long likeCount;
    private SourceType sourceType;
    private String tweetUrl;
    private UserThumbnailResponse user;
    private List<ProductSearchResponse> products;

    public static PopularSwatchResponse of(Swatch swatch, List<SwatchLink> swatchLinks, boolean liked) {
        return PopularSwatchResponse.builder()
                .id(swatch.getId())
                .title(swatch.getTitle())
                .content(swatch.getContent())
                .createdAt(swatch.getCreatedAt())
                .imageUrls(swatch.getImageUrls())
                .liked(liked)
                .likeCount(swatch.getLikeCount())
                .sourceType(swatch.getSourceType())
                .tweetUrl(swatch.getTweetUrl())
                .user(UserThumbnailResponse.from(swatch.getUser()))
                .products(swatchLinks.stream().map(SwatchLink::getProduct).map(ProductSearchResponse::of).toList())
                .build();
    }
}
