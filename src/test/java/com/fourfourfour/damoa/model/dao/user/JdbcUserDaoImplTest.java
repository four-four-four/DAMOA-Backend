package com.fourfourfour.damoa.model.dao.user;

import com.fourfourfour.damoa.model.dto.user.UserDto;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;


@SpringBootTest
@Transactional
class JdbcUserDaoImplTest {

    @Autowired
    private UserDao userDao;

    @Test
    public void testInsertUser(){
        //given
        UserDto user = new UserDto();
        user.setUserEmail("test@test.com");
        user.setUserPw("1234");
        user.setUserJob("대학생");
        user.setUserGender("female");
        user.setUserNickname("테스트");
        user.setUserPromotionAgree(true);
        user.setUserPrivacyAgree(true);
        user.setUserLocationAgree(true);
        user.setUserServiceAgree(true);
        //when

        userDao.insertUser(user);

        //then
        String findNickname = userDao.selectByNickname(user.getUserNickname());
        Assertions.assertThat(user.getUserNickname()).isEqualTo(findNickname);
    }

    @Test
    public void testSelectEmailByEmail(){
        //given
        String userEmail = "test4@test.com";

        //when
        String result = userDao.selectEmailByEmail(userEmail);

        //then
        Assertions.assertThat(result).isNull(); // Null일 때 통과
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