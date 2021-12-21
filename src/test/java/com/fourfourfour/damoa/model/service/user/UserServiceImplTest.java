package com.fourfourfour.damoa.model.service.user;

import com.fourfourfour.damoa.model.dao.user.UserDao;
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
class UserServiceImplTest {

    @Autowired
    UserService userService;
    @Autowired
    UserDao userDao;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    public void testRegister() {
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
        userService.register(user);

        //then
        UserDto result = userDao.selectUser(user.getUserIdx());
        Assertions.assertThat(user).isEqualTo(result);
    }

    @Test
    public void testIsEmailDuplication() {
        String userEmail = "test3@test.com";

        boolean result = userService.isEmailDuplication(userEmail);

        Assertions.assertThat(result).isTrue(); // True일 때 중복
    }

    @Test
    public void testIsNicknameDuplication() {
        String userNickname = "testtest11";

        boolean result = userService.isNicknameDuplication(userNickname);

        Assertions.assertThat(result).isTrue(); // True일 때 중복
    }
}