package com.mosquito.mosquitowiki.product.repository;

import com.mosquito.mosquitowiki.product.domain.Brand;
import com.mosquito.mosquitowiki.product.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long>, ProductRepositoryCustom {

    Optional<Product> findBySlug(String slug);

    boolean existsBySlug(String slug);

    List<Product> findAllByBrand(Brand brand);

    List<Product> findAllByParentAndParentIsNotNull(Product parent);
}
