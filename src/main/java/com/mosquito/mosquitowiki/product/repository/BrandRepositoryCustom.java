package com.mosquito.mosquitowiki.product.repository;

import com.mosquito.mosquitowiki.product.domain.Brand;

import java.util.List;

public interface BrandRepositoryCustom {
    List<Brand> searchByName(String query);
}
