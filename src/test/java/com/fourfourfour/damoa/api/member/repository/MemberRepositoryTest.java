package com.fourfourfour.damoa.api.member.repository;

import com.fourfourfour.damoa.api.member.entity.Member;
import com.fourfourfour.damoa.api.member.service.MemberService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.time.LocalDate;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
class MemberRepositoryTest {

    @Autowired
    private MemberService memberService;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private EntityManager em;

    private Member member1, member2;

    @BeforeEach
    public void setUp() {
        memberService.deleteAll();

        member1 = Member.builder()
                .email("test1@damoa.com")
                .password(passwordEncoder.encode("Abcdefg1!"))
                .nickname("testNickname1")
                .gender(Member.Gender.FEMALE)
                .birthDate(LocalDate.of(1997, 10, 11))
                .job("대학생")
                .role(Member.Role.MEMBER)
                .serviceTerm(true)
                .privacyTerm(true)
                .build();

        member2 = Member.builder()
                .email("test2@damoa.com")
                .password(passwordEncoder.encode("Abcdefg1!"))
                .nickname("testNickname2")
                .gender(Member.Gender.FEMALE)
                .birthDate(LocalDate.of(1995, 10, 11))
                .job("고등학생")
                .role(Member.Role.MEMBER)
                .serviceTerm(true)
                .privacyTerm(true)
                .build();
    }

    @Test
    @DisplayName("이메일 중복 체크")
    void existsByEmail() {
        String email = member1.getEmail();

        // 이메일 중복 체크
        boolean hasEmail = memberRepository.existsByEmail(email);
        assertThat(hasEmail).isFalse();

        // 회원가입
        memberRepository.save(member1);
        em.flush();
        em.clear();

        // 이메일 중복 체크
        hasEmail = memberRepository.existsByEmail(email);
        assertThat(hasEmail).isTrue();
    }

    @Test
    @DisplayName("닉네임 중복 체크")
    void existsByNickname() {
        String nickname = member1.getNickname();

        // 닉네임 중복 체크
        boolean hasNickname = memberRepository.existsByNickname(nickname);
        assertThat(hasNickname).isFalse();

        // 회원가입
        memberRepository.save(member1);
        em.flush();
        em.clear();

        // 닉네임 중복 체크
        hasNickname = memberRepository.existsByNickname(nickname);
        assertThat(hasNickname).isTrue();
    }
}