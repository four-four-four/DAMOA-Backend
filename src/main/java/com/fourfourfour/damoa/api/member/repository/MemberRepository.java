package com.fourfourfour.damoa.api.member.repository;

import com.fourfourfour.damoa.api.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {
    int countByEmail(String memberEmail);
    int countByNickname(String memberNickname);
}
