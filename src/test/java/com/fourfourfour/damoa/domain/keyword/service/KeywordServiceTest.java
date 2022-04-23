package com.fourfourfour.damoa.domain.keyword.service;

import com.fourfourfour.damoa.common.constant.ErrorMessage;
import com.fourfourfour.damoa.domain.keyword.entity.Keyword;
import com.fourfourfour.damoa.domain.keyword.entity.MemberKeyword;
import com.fourfourfour.damoa.domain.keyword.repository.KeywordRepository;
import com.fourfourfour.damoa.domain.keyword.repository.MemberKeywordRepository;
import com.fourfourfour.damoa.domain.keyword.service.dto.KeywordDto;
import com.fourfourfour.damoa.domain.member.controller.dto.MemberRequestDto;
import com.fourfourfour.damoa.domain.member.entity.Member;
import com.fourfourfour.damoa.domain.member.service.MemberService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

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

    @Test
    @DisplayName("회원 등록 키워드 목록 조회 - 예외 처리 : 회원 엔티티가 없는 경우")
    void findMemberKeywordListWhenNullMember() {
        /**
         * 예외 처리 검증
         */
        assertThatThrownBy(() -> keywordService.keywordBasicList(1L))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(ErrorMessage.NULL_MEMBER);
    }

    @Test
    @DisplayName("회원 등록 키워드 목록 조회 - 성공 : 키워드가 없는 경우")
    void findMemberKeywordListNonExist() {
        /**
         * 회원가입
         */
        Member savedMember = memberService.register(memberRegisterDto1.toServiceDto());

        /**
         * 회원 등록 키워드 목록 조회
         */
        List<KeywordDto.BasicDto> keywordBasicList = keywordService.keywordBasicList(savedMember.getSeq());

        /**
         * 데이터 검증
         */
        assertThat(keywordBasicList).isNull();
    }

    @Test
    @DisplayName("회원 등록 키워드 목록 조회 - 성공 : 키워드가 있는 경우")
    void findMemberKeywordListExist() {
        /**
         * 회원가입
         */
        Member savedMember = memberService.register(memberRegisterDto1.toServiceDto());

        /**
         * 키워드 등록 서비스 메서드가 없기 때문에 레파지토리 메서드로 대체합니다.
         */
        Keyword savedKeyword1 = keywordRepository.save(Keyword.builder()
                .name("키워드1")
                .build());
        Keyword savedKeyword2 = keywordRepository.save(Keyword.builder()
                .name("키워드2")
                .build());
        em.flush();
        em.clear();

        /**
         * 회원 키워드 등록 서비스 메서드가 없기 때문에 레파지토리 메서드로 대체합니다.
         */
        MemberKeyword savedMemberKeyword1 = memberKeywordRepository.save(MemberKeyword.builder()
                .member(savedMember)
                .keyword(savedKeyword1)
                .build());
        MemberKeyword savedMemberKeyword2 = memberKeywordRepository.save(MemberKeyword.builder()
                .member(savedMember)
                .keyword(savedKeyword2)
                .build());
        em.flush();
        em.clear();

        /**
         * 회원 등록 키워드 목록 조회
         */
        List<KeywordDto.BasicDto> keywordBasicList = keywordService.keywordBasicList(savedMember.getSeq());

        /**
         * 데이터 검증
         */
        assertThat(keywordBasicList.size()).isEqualTo(2);

        KeywordDto.BasicDto keywordBasicDto1 = keywordBasicList.get(0);
        assertThat(keywordBasicDto1.getSeq()).isEqualTo(savedKeyword1.getSeq());
        assertThat(keywordBasicDto1.getName()).isEqualTo(savedKeyword1.getName());

        KeywordDto.BasicDto keywordBasicDto2 = keywordBasicList.get(1);
        assertThat(keywordBasicDto2.getSeq()).isEqualTo(savedKeyword2.getSeq());
        assertThat(keywordBasicDto2.getName()).isEqualTo(savedKeyword2.getName());
    }
}