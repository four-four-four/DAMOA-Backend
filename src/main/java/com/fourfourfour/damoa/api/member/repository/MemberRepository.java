package com.fourfourfour.damoa.api.member.repository;

import com.fourfourfour.damoa.api.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {
    boolean existsByEmail(String memberEmail);
    boolean existsByNickname(String memberNickname);
}
