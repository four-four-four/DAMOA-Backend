package com.fourfourfour.damoa.model.dao.user;

import com.fourfourfour.damoa.model.dto.user.UserDto;
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
class JdbcUserDaoImplTest {

    @Autowired
    private UserDao userDao;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    @DisplayName("회원가입")
    public void insertUser(){
        //given
        UserDto user = new UserDto();
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
        userDao.insertUser(user);

        //then
        UserDto findUser = userDao.selectUserByUserEmail(user.getUserEmail());
        assertThat(user).isEqualTo(findUser);
    }

    @Test
    @DisplayName("이메일 중복 체크")
    public void selectCountByEmail(){
        //given
        String email = "test3@test.com";

        UserDto user = new UserDto();
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
        int count = userDao.selectCountByEmail(email);
        assertThat(count).isEqualTo(0);

        //then
        userDao.insertUser(user);
        int result = userDao.selectCountByEmail(email);
        assertThat(result).isEqualTo(1);
    }

    @Test
    @DisplayName("닉네임 중복 체크")
    public void selectCountByNickname(){
        //given
        String nickname = "테스트";

        UserDto user = new UserDto();
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
        int count = userDao.selectCountByNickname(nickname);
        assertThat(count).isEqualTo(0);

        //then
        userDao.insertUser(user);
        int result = userDao.selectCountByNickname(nickname);
        assertThat(result).isEqualTo(1);
    }
}