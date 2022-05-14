package com.fourfourfour.damoa.domain.member.repository;

import com.fourfourfour.damoa.domain.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {

    boolean existsByEmail(String memberEmail);

    boolean existsByNickname(String memberNickname);

    Optional<Member> findByEmail(String email);

    @Query("SELECT m FROM Member m WHERE m.seq = :seq AND m.isDeleted = FALSE")
    Optional<Member> findBySeq(@Param("seq") Long seq);
}
