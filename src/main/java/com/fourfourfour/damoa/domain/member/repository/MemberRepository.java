package com.fourfourfour.damoa.domain.member.repository;

import com.fourfourfour.damoa.domain.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {

    boolean existsByEmail(String memberEmail);

    boolean existsByNickname(String memberNickname);

    Optional<Member> findByEmail(String email);

    Optional<Member> findBySeq(Long seq);
}
