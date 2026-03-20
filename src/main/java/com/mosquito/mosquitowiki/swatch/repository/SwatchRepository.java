package com.mosquito.mosquitowiki.swatch.repository;

import com.mosquito.mosquitowiki.product.domain.Product;
import com.mosquito.mosquitowiki.swatch.Swatch;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SwatchRepository extends JpaRepository<Swatch, Long> {
    Page<Swatch> findBySwatchLinks_ProductOrderByCreatedAtDesc(Product product, Pageable pageable);
}
