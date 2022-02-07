package com.fourfourfour.damoa.api.member.repository;

import com.fourfourfour.damoa.api.member.entity.Member;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import static org.assertj.core.api.Assertions.*;


@SpringBootTest
@Transactional
class MemberRepositoryTest {

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    @DisplayName("이메일 중복 체크")
    void existsByEmail() {
        String email = "test@test.com";

        boolean hasEmail = memberRepository.existsByEmail(email);
        assertThat(hasEmail).isFalse();

        memberRepository.save(Member.builder()
                .email(email)
                .password(passwordEncoder.encode("Abcdefg1!"))
                .nickname("백엔드테스트")
                .gender("female")
                .birthDate(LocalDateTime.of(LocalDate.of(1997, 10, 11), LocalTime.now()))
                .job("대학생")
                .role("ROLE_member")
                .serviceTerm(true)
                .privacyTerm(true)
                .build());

        hasEmail = memberRepository.existsByEmail(email);
        assertThat(hasEmail).isTrue();
    }

    @Test
    @DisplayName("닉네임 중복 체크")
    void existsByNickname() {
        String memberNickname = "백엔드테스트";

        boolean isNickname = memberRepository.existsByNickname(memberNickname);
        assertThat(isNickname).isFalse();

        memberRepository.save(Member.builder()
                .email("test@test.com")
                .password(passwordEncoder.encode("Abcdefg1!"))
                .nickname(memberNickname)
                .gender("female")
                .birthDate(LocalDateTime.of(LocalDate.of(1997, 10, 11), LocalTime.now()))
                .job("대학생")
                .role("ROLE_member")
                .serviceTerm(true)
                .privacyTerm(true)
                .build());

        boolean result = memberRepository.existsByNickname(memberNickname);
        assertThat(result).isTrue();
    }
}