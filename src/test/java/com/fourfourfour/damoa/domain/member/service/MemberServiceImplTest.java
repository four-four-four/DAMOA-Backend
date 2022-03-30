package com.fourfourfour.damoa.domain.member.service;

import com.fourfourfour.damoa.domain.member.controller.dto.MemberRequestDto;
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
class MemberServiceImplTest {

    @Autowired
    private MemberService memberService;

    @Autowired
    private EntityManager em;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private MemberRequestDto.RegisterDto registerDto1;

    @BeforeEach
    public void setUp() {
        memberService.deleteAll();

        registerDto1 = MemberRequestDto.RegisterDto.builder()
                .email("test1@damoa.com")
                .password(passwordEncoder.encode("Abcdefg1!"))
                .nickname("testNickname1")
                .gender("female")
                .birthDate(LocalDate.of(1997, 10, 11))
                .job("대학생")
                .serviceTerm(true)
                .privacyTerm(true)
                .build();
    }

    @Test
    @DisplayName("이메일 중복 체크")
    void isEmailDuplication() {
        String email = registerDto1.getEmail();

        // 이메일 중복 체크
        boolean isUsingEmail = memberService.isEmailDuplication(email);
        assertThat(isUsingEmail).isFalse();

        // 회원가입
        memberService.register(registerDto1.toServiceDto());

        // 이메일 중복 체크
        isUsingEmail = memberService.isEmailDuplication(email);
        assertThat(isUsingEmail).isTrue();
    }

    @Test
    @DisplayName("닉네임 중복 체크")
    void isNicknameDuplication() {
        String nickname = registerDto1.getNickname();

        // 닉네임 중복 체크
        boolean isUsingNickname = memberService.isNicknameDuplication(nickname);
        assertThat(isUsingNickname).isFalse();

        // 회원가입
        memberService.register(registerDto1.toServiceDto());

        // 닉네임 중복 체크
        isUsingNickname = memberService.isNicknameDuplication(nickname);
        assertThat(isUsingNickname).isTrue();
    }
}