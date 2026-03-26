package com.mosquito.mosquitowiki.swatch.repository;

import com.mosquito.mosquitowiki.product.domain.Product;
import com.mosquito.mosquitowiki.swatch.Swatch;
import com.mosquito.mosquitowiki.swatch.SwatchLink;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SwatchLinkRepository extends JpaRepository<SwatchLink, Long> {
    long countByProduct(Product product);

    List<SwatchLink> findBySwatchIn(List<Swatch> swatches);
}
