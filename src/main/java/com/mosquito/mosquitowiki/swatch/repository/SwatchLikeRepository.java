package com.mosquito.mosquitowiki.swatch.repository;

import com.mosquito.mosquitowiki.swatch.Swatch;
import com.mosquito.mosquitowiki.swatch.SwatchLike;
import com.mosquito.mosquitowiki.users.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SwatchLikeRepository extends JpaRepository<SwatchLike, Long> {

    boolean existsBySwatchAndUser(Swatch swatch, User user);

    void deleteBySwatchAndUser(Swatch swatch, User user);

    long countBySwatch(Swatch swatch);

    List<SwatchLike> findByUserAndSwatchIn(User user, List<Swatch> swatches);
}
