package com.fourfourfour.damoa.api.member.service;

import com.fourfourfour.damoa.api.member.dto.req.ReqRegisterMemberDto;
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
class MemberServiceImplTest {

    @Autowired
    MemberService memberService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    @DisplayName("이메일 중복 체크")
    void isEmailDuplication() {
        String email = "test@test.com";

        ReqRegisterMemberDto reqMember = ReqRegisterMemberDto.builder()
                .email(email)
                .password(passwordEncoder.encode("Abcdefg1!"))
                .nickname("백엔드테스트")
                .gender("female")
                .birthDate(LocalDate.of(1997, 10, 11))
                .job("대학생")
                .serviceTerm(true)
                .privacyTerm(true)
                .build();

        boolean isEmail = memberService.isEmailDuplication(email);
        assertThat(isEmail).isFalse();

        memberService.register(reqMember);
        boolean result = memberService.isEmailDuplication(email);
        assertThat(result).isTrue();
    }

    @Test
    @DisplayName("닉네임 중복 체크")
    void isNicknameDuplication() {
        String nickname = "백엔드테스트";

        ReqRegisterMemberDto reqMember = ReqRegisterMemberDto.builder()
                .email("test@test.com")
                .password(passwordEncoder.encode("Abcdefg1!"))
                .nickname(nickname)
                .gender("female")
                .birthDate(LocalDate.of(1997, 10, 11))
                .job("대학생")
                .serviceTerm(true)
                .privacyTerm(true)
                .build();

        boolean isNickname = memberService.isNicknameDuplication(nickname);
        assertThat(isNickname).isFalse();

        memberService.register(reqMember);
        boolean result = memberService.isNicknameDuplication(nickname);
        assertThat(result).isTrue();
    }
}