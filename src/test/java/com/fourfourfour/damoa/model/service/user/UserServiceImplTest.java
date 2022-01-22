package com.fourfourfour.damoa.model.service.user;

import com.fourfourfour.damoa.api.member.service.MemberService;
import com.fourfourfour.damoa.api.member.dao.MemberDao;
import com.fourfourfour.damoa.api.member.dto.MemberDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class UserServiceImplTest {

    @Autowired
    MemberService userService;
    @Autowired
    MemberDao userDao;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    @DisplayName("회원가입")
    public void register() {
        //given
        MemberDto user = new MemberDto();
        user.setUserEmail("test@test.com");
        user.setUserPw(passwordEncoder.encode("5555"));
        user.setUserJob("대학생");
        user.setUserGender("female");
        user.setUserNickname("테스트");
        user.setUserBirthDate(LocalDate.of(1997, 10, 11));
        user.setRole("ROLE_USER");
        user.setUserPromotionAgree(true);
        user.setUserPrivacyAgree(true);
        user.setUserLocationAgree(true);
        user.setUserServiceAgree(true);

        //when
        userService.register(user);

        //then
        MemberDto findUser = userDao.selectUserByUserEmail(user.getUserEmail());
        assertThat(user).isEqualTo(findUser);
    }

    @Test
    @DisplayName("이메일 중복 체크")
    public void isEmailDuplication() {
        //given
        String email = "test3@test.com";

        MemberDto user = new MemberDto();
        user.setUserEmail(email);
        user.setUserPw(passwordEncoder.encode("5555"));
        user.setUserJob("대학생");
        user.setUserGender("female");
        user.setUserNickname("테스트");
        user.setUserBirthDate(LocalDate.of(1997, 10, 11));
        user.setRole("ROLE_USER");
        user.setUserPromotionAgree(true);
        user.setUserPrivacyAgree(true);
        user.setUserLocationAgree(true);
        user.setUserServiceAgree(true);

        //when
        boolean isEmail = userService.isEmailDuplication(email);
        assertThat(isEmail).isFalse();

        //then
        userDao.insertUser(user);
        boolean result = userService.isEmailDuplication(email);
        assertThat(result).isTrue();
    }

    @Test
    @DisplayName("닉네임 중복 체크")
    public void isNicknameDuplication() {
        //given
        String nickname = "테스트";

        MemberDto user = new MemberDto();
        user.setUserEmail("test3@test.com");
        user.setUserPw(passwordEncoder.encode("5555"));
        user.setUserJob("대학생");
        user.setUserGender("female");
        user.setUserNickname(nickname);
        user.setUserBirthDate(LocalDate.of(1997, 10, 11));
        user.setRole("ROLE_USER");
        user.setUserPromotionAgree(true);
        user.setUserPrivacyAgree(true);
        user.setUserLocationAgree(true);
        user.setUserServiceAgree(true);

        //when
        boolean isNickname = userService.isNicknameDuplication(nickname);
        assertThat(isNickname).isFalse();

        //then
        userDao.insertUser(user);
        boolean result = userService.isNicknameDuplication(nickname);
        assertThat(result).isTrue();
    }
}