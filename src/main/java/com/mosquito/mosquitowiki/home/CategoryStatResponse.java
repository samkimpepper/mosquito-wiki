package com.mosquito.mosquitowiki.home;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CategoryStatResponse {
    private String category;
    private long productCount;
}
