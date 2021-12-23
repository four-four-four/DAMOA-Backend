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
    public void testSelectEmailByEmail(){
        //given
        UserDto user = new UserDto();
        user.setUserEmail("test3@test.com");
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
        Integer emailCount = userDao.selectEmailByEmail(user.getUserEmail());
        assertThat(emailCount).as("[회원가입 전] 이메일이 중복됩니다.").isEqualTo(0);

        //then
        userDao.insertUser(user);
        Integer result = userDao.selectEmailByEmail(user.getUserEmail());
        assertThat(result).as("[회원가입 후] 이메일이 중복됩니다.").isEqualTo(0);

    }

    @Test
    public void testSelectByNickname(){
        //given
        UserDto user = new UserDto();
        user.setUserEmail("test3@test.com");
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
        Integer nicknameCount = userDao.selectByNickname(user.getUserNickname());
        assertThat(nicknameCount).as("[회원가입 전] 닉네임이 중복됩니다.").isEqualTo(0);

        //then
        userDao.insertUser(user);
        Integer result = userDao.selectByNickname(user.getUserNickname());
        assertThat(result).as("[회원가입 후] 닉네임이 중복됩니다.").isEqualTo(0);
    }
}