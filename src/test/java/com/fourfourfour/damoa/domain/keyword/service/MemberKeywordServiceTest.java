package com.fourfourfour.damoa.domain.keyword.service;

import com.fourfourfour.damoa.common.constant.ErrorMessage;
import com.fourfourfour.damoa.domain.keyword.entity.Keyword;
import com.fourfourfour.damoa.domain.keyword.repository.KeywordRepository;
import com.fourfourfour.damoa.domain.keyword.repository.MemberKeywordRepository;
import com.fourfourfour.damoa.domain.member.controller.dto.MemberRequestDto;
import com.fourfourfour.damoa.domain.member.repository.MemberRepository;
import com.fourfourfour.damoa.domain.member.service.MemberService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
@Transactional
class MemberKeywordServiceTest {

    @Autowired
    private EntityManager em;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private KeywordRepository keywordRepository;

    @Autowired
    private MemberKeywordRepository memberKeywordRepository;

    @Autowired
    private MemberKeywordService memberKeywordService;

    @Autowired
    private MemberService memberService;

    private Keyword keyword1;

    private String newKeywordName1;

    private MemberRequestDto.RegisterDto registerDto1;

    @BeforeEach
    void setUp() {
        memberRepository.deleteAll();
        keywordRepository.deleteAll();
        memberKeywordRepository.deleteAll();

        newKeywordName1 = "키워드1";

        keyword1 = Keyword.builder()
                .name(newKeywordName1)
                .build();

        registerDto1 = MemberRequestDto.RegisterDto.builder()
                .email("test1@damoa.com")
                .password("Abcdefg1!")
                .nickname("testNickname1")
                .gender("female")
                .birthDate(LocalDate.of(1997, 10, 11))
                .job("대학생")
                .serviceTerm(true)
                .privacyTerm(true)
                .build();
    }

    @Test
    @DisplayName("회원 키워드 등록 - 예외 발생 : DB에 회원 데이터가 없음")
    void registerKeywordFailWhenNotExistsMember() {
        // given
        Keyword savedKeyword1 = keywordRepository.save(keyword1);
        em.flush();
        em.clear();

        // when

        // then
        assertThatThrownBy(() -> memberKeywordService.register(savedKeyword1.getName(), 0L))
                .isInstanceOf(IllegalStateException.class)
                .hasMessage(ErrorMessage.NULL_MEMBER);
    }
}