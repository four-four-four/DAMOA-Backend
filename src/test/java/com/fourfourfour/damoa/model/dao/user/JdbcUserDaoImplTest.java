package com.fourfourfour.damoa.model.dao.user;

import com.fourfourfour.damoa.model.dto.user.UserDto;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@SpringBootTest
@Transactional
class JdbcUserDaoImplTest {

    @Autowired
    private UserDao userDao;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    public void testInsertUser(){
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
        UserDto result = userDao.selectUser(user.getUserIdx());
        Assertions.assertThat(user).isEqualTo(result);
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
        String result = userDao.selectEmailByEmail(user.getUserEmail()); // 이메일 중복 체크

        //then
        if (result == null) {
            userDao.insertUser(user); // 중복되지 않기 때문에 회원가입
        }
        else {
            Assertions.assertThat(result).isNotNull(); // Not null일 때 중복
        }
    }

    @Test
    public void testSelectByNickname(){
        //given
        String userNickname = "test4";

        //when
        String result = userDao.selectByNickname(userNickname);

        //then
        Assertions.assertThat(result).isNull(); // Null일 때 통과
    }
}