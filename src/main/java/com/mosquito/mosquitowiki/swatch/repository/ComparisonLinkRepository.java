package com.mosquito.mosquitowiki.swatch.repository;

import com.mosquito.mosquitowiki.swatch.ComparisonLink;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ComparisonLinkRepository extends JpaRepository<ComparisonLink, Long> {
}
