package com.fourfourfour.damoa.domain.keyword.repository;

import com.fourfourfour.damoa.domain.keyword.entity.BannedKeyword;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BannedKeywordRepository extends JpaRepository<BannedKeyword, Long> {

    boolean existsByName(String name);
}
