package com.mosquito.mosquitowiki.swatch.repository;

import com.mosquito.mosquitowiki.swatch.ComparisonSwatch;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ComparisonSwatchRepository extends JpaRepository<ComparisonSwatch, Long> {
}
