package com.mosquito.mosquitowiki.swatch.dto;

import com.mosquito.mosquitowiki.swatch.SourceType;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class SwatchCreateRequest {
    private List<String> productSlugs;
    private String content;
    private SourceType sourceType;
    private String tweetUrl;
}
