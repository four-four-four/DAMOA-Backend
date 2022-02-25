package com.fourfourfour.damoa.api.member.repository;

import com.fourfourfour.damoa.api.member.entity.Member;
import com.fourfourfour.damoa.api.member.enums.Gender;
import com.fourfourfour.damoa.api.member.enums.Role;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
class MemberRepositoryTest {

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private Member member1;

    @BeforeEach
    public void setUp() {
        memberRepository.deleteAll();

        member1 = Member.builder()
                .email("test1@damoa.com")
                .password(passwordEncoder.encode("Abcdefg1!"))
                .nickname("testNickname1")
                .gender(Gender.FEMALE)
                .birthDate(LocalDate.of(1997, 10, 11))
                .job("대학생")
                .role(Role.MEMBER)
                .serviceTerm(true)
                .privacyTerm(true)
                .build();
    }

    @Test
    @DisplayName("이메일 중복 체크")
    void existsByEmail() {
        String email = member1.getEmail();

        boolean hasEmail = memberRepository.existsByEmail(email);
        assertThat(hasEmail).isFalse();

        memberRepository.save(member1);
        hasEmail = memberRepository.existsByEmail(email);
        assertThat(hasEmail).isTrue();
    }

    @Test
    @DisplayName("닉네임 중복 체크")
    void existsByNickname() {
        String nickname = member1.getNickname();

        boolean hasNickname = memberRepository.existsByNickname(nickname);
        assertThat(hasNickname).isFalse();

        memberRepository.save(member1);

        hasNickname = memberRepository.existsByNickname(nickname);
        assertThat(hasNickname).isTrue();
    }
}