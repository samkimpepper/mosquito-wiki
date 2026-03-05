package com.mosquito.mosquitowiki.swatch.repository;

import com.mosquito.mosquitowiki.swatch.Swatch;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SwatchRepository extends JpaRepository<Swatch, Long> {
}
