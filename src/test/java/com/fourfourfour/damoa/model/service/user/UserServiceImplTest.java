package com.fourfourfour.damoa.model.service.user;

import com.fourfourfour.damoa.model.dto.user.UserDto;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
class UserServiceImplTest {

    @Autowired
    UserService userService;

    @Test
    public void testRegister() {
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
        userService.register(user);

        //then
        // 서비스 단위에서 이 부분이 필요한지는 좀 더 공부해야 될 느낌입니다..!!
        boolean resultEmail = userService.isEmailDuplication(user.getUserEmail());
        Assertions.assertThat(resultEmail).isFalse(); // False라면 중복되지 않기 때문에 가입 가능
        boolean resultNickname = userService.isEmailDuplication(user.getUserNickname());
        Assertions.assertThat(resultNickname).isFalse(); // False라면 중복되지 않기 때문에 가입 가능
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