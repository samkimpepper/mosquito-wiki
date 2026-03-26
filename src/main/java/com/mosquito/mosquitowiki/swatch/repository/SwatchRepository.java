package com.mosquito.mosquitowiki.swatch.repository;

import com.mosquito.mosquitowiki.product.domain.Product;
import com.mosquito.mosquitowiki.swatch.Swatch;
import com.mosquito.mosquitowiki.users.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SwatchRepository extends JpaRepository<Swatch, Long> {
    //Page<Swatch> findBySwatchLinks_ProductOrderByCreatedAtDesc(Product product, Pageable pageable);
    @Query("SELECT s FROM Swatch s JOIN SwatchLink sl ON sl.swatch = s WHERE sl.product.slug = :slug ORDER BY s.createdAt DESC")
    Page<Swatch> findByProductSlug(@Param("slug") String slug, Pageable pageable);

    @Query("SELECT s FROM Swatch s JOIN FETCH s.user ORDER BY s.likeCount DESC, s.viewCount DESC")
    Page<Swatch> findPopularSwatches(@Param("user") User user, Pageable pageable);
}
