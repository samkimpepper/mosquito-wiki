package com.mosquito.mosquitowiki.swatch.dto;

import com.mosquito.mosquitowiki.swatch.SourceType;
import com.mosquito.mosquitowiki.swatch.Swatch;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Builder
public class SwatchDetailResponse {
    private Long id;
    private String content;
    private LocalDateTime createdAt;
    private List<String> imageUrls;
    private boolean liked;
    private long likeCount;
    private SourceType sourceType;
    private String tweetUrl;

    public static SwatchDetailResponse from(Swatch swatch) {
        return SwatchDetailResponse.builder()
                .id(swatch.getId())
                .content(swatch.getContent())
                .createdAt(swatch.getCreatedAt())
                .imageUrls(swatch.getImageUrls())
                .liked(false)
                .likeCount(swatch.getLikeCount())
                .sourceType(swatch.getSourceType())
                .tweetUrl(swatch.getTweetUrl())
                .build();
    }
}
