package com.fourfourfour.damoa.domain.keyword.repository;

import com.fourfourfour.damoa.domain.keyword.entity.Keyword;
import com.fourfourfour.damoa.domain.keyword.entity.MemberKeyword;
import com.fourfourfour.damoa.domain.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MemberKeywordRepository extends JpaRepository<MemberKeyword, Long> {

    Optional<List<MemberKeyword>> findAllByMember(Member member);

    Optional<MemberKeyword> findByMemberAndKeyword(Member member, Keyword keyword);
}
