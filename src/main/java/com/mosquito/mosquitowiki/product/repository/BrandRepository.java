package com.mosquito.mosquitowiki.product.repository;

import com.mosquito.mosquitowiki.product.domain.Brand;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BrandRepository extends JpaRepository<Brand, Long>, BrandRepositoryCustom {

    boolean existsBySlug(String slug);
}
