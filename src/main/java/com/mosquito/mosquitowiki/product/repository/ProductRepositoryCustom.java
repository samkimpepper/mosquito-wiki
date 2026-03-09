package com.mosquito.mosquitowiki.product.repository;

import com.mosquito.mosquitowiki.product.domain.Product;

import java.util.List;

public interface ProductRepositoryCustom {
    List<Product> searchByName(String query);
}
