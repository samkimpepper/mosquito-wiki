package com.mosquito.mosquitowiki.swatch.repository;

import com.mosquito.mosquitowiki.swatch.SwatchTag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SwatchTagRepository extends JpaRepository<SwatchTag, Long> {
}
