package com.fourfourfour.damoa.domain.keyword.service;

import com.fourfourfour.damoa.domain.keyword.repository.KeywordRepository;
import com.fourfourfour.damoa.domain.keyword.repository.MemberKeywordRepository;
import com.fourfourfour.damoa.domain.member.controller.dto.MemberRequestDto;
import com.fourfourfour.damoa.domain.member.service.MemberService;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.time.LocalDate;

@SpringBootTest
@Transactional
class KeywordServiceTest {

    @Autowired
    private EntityManager em;

    @Autowired
    private MemberService memberService;

    @Autowired
    private KeywordService keywordService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private KeywordRepository keywordRepository;

    @Autowired
    private MemberKeywordRepository memberKeywordRepository;

    private MemberRequestDto.RegisterDto memberRegisterDto1;

    @BeforeEach
    void setUp() {
        memberService.deleteAll();
        keywordService.deleteAll();

        memberRegisterDto1 = MemberRequestDto.RegisterDto.builder()
                .email("test1@damoa.com")
                .password(passwordEncoder.encode("Abcdefg1!"))
                .nickname("testNickname2")
                .gender("male")
                .birthDate(LocalDate.of(1998, 10, 11))
                .job("전문직")
                .serviceTerm(true)
                .privacyTerm(true)
                .build();
    }
}