package com.fourfourfour.damoa.model.dao.user;

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
class JdbcUserDaoImplTest {

    @Autowired
    private MemberDao userDao;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    @DisplayName("회원가입")
    public void insertMember(){
        //given
        MemberDto memberDto = new MemberDto();
        memberDto.setEmail("test@test.com");
        memberDto.setPw(passwordEncoder.encode("5555"));
        memberDto.setJob("대학생");
        memberDto.setGender("female");
        memberDto.setNickname("테스트");
        memberDto.setBirthDate(LocalDate.of(1997, 10, 11));
        memberDto.setRole("ROLE_USER");
        memberDto.setServiceTerm(true);
        memberDto.setPrivacyTerm(true);

        //when
        userDao.insertUser(memberDto);

        //then
        MemberDto findUser = userDao.selectUserByUserEmail(memberDto.getEmail());
        assertThat(memberDto).isEqualTo(findUser);
    }

    @Test
    @DisplayName("이메일 중복 체크")
    public void selectCountByEmail(){
        //given
        String email = "test3@test.com";

        MemberDto memberDto = new MemberDto();
        memberDto.setEmail(email);
        memberDto.setPw(passwordEncoder.encode("5555"));
        memberDto.setJob("대학생");
        memberDto.setGender("female");
        memberDto.setNickname("테스트");
        memberDto.setBirthDate(LocalDate.of(1997, 10, 11));
        memberDto.setRole("ROLE_USER");
        memberDto.setServiceTerm(true);
        memberDto.setPrivacyTerm(true);

        //when
        int count = userDao.selectCountByEmail(email);
        assertThat(count).isEqualTo(0);

        //then
        userDao.insertUser(memberDto);
        int result = userDao.selectCountByEmail(email);
        assertThat(result).isEqualTo(1);
    }

    @Test
    @DisplayName("닉네임 중복 체크")
    public void selectCountByNickname(){
        //given
        String nickname = "테스트";

        MemberDto memberDto = new MemberDto();
        memberDto.setEmail("test3@test.com");
        memberDto.setPw(passwordEncoder.encode("5555"));
        memberDto.setJob("대학생");
        memberDto.setGender("female");
        memberDto.setNickname(nickname);
        memberDto.setBirthDate(LocalDate.of(1997, 10, 11));
        memberDto.setRole("ROLE_USER");
        memberDto.setServiceTerm(true);
        memberDto.setPrivacyTerm(true);

        //when
        int count = userDao.selectCountByNickname(nickname);
        assertThat(count).isEqualTo(0);

        //then
        userDao.insertUser(memberDto);
        int result = userDao.selectCountByNickname(nickname);
        assertThat(result).isEqualTo(1);
    }
}