package com.mosquito.mosquitowiki.home;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class DashBoardResponse {
    private long brandCount;
    private long productCount;
    private long swatchCount;
    private List<CategoryStatResponse> categoryStats;
}
