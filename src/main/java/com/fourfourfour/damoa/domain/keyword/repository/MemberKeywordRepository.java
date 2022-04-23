package com.fourfourfour.damoa.domain.keyword.repository;

import com.fourfourfour.damoa.domain.keyword.entity.MemberKeyword;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberKeywordRepository extends JpaRepository<MemberKeyword, Long> {
}
