package com.fourfourfour.damoa.domain.keyword.repository;

import com.fourfourfour.damoa.domain.keyword.entity.Keyword;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface KeywordRepository extends JpaRepository<Keyword, Long> {

    @Query("SELECT k FROM Keyword k WHERE k.name = :name AND k.isDeleted = FALSE")
    Optional<Keyword> findByName(@Param("name") String name);
}
