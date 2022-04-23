package com.fourfourfour.damoa.domain.keyword.repository;

import com.fourfourfour.damoa.domain.keyword.entity.Keyword;
import com.fourfourfour.damoa.domain.keyword.entity.MemberKeyword;
import com.fourfourfour.damoa.domain.member.entity.Member;
import com.fourfourfour.damoa.domain.member.repository.MemberRepository;
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

@SpringBootTest
@Transactional
class MemberKeywordRepositoryTest {

    @Autowired
    private EntityManager em;

    @Autowired
    private MemberKeywordRepository memberKeywordRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private KeywordRepository keywordRepository;

    private Keyword keyword1, keyword2;

    private Member member1;

    @BeforeEach
    void setUp() {
        memberRepository.deleteAll();
        keywordRepository.deleteAll();
        em.flush();
        em.clear();

        keyword1 = Keyword.builder()
                .name("키워드1")
                .build();

        keyword2 = Keyword.builder()
                .name("키워드2")
                .build();

        member1 = Member.builder()
                .email("test1@damoa.com")
                .password(passwordEncoder.encode("Abcdefg1!"))
                .nickname("testNickname1")
                .gender(Member.Gender.FEMALE)
                .birthDate(LocalDate.of(1997, 10, 11))
                .job("대학생")
                .role(Member.Role.MEMBER)
                .serviceTerm(true)
                .privacyTerm(true)
                .build();
    }

    @Test
    @DisplayName("회원 엔티티로 회원 키워드 엔티티 목록 조회 - 키워드가 없는 경우")
    void findMemberKeywordListNonExistByMember() {
        /**
         * 테스트용 데이터 생성
         */
        Member savedMember = memberRepository.save(member1);
        em.flush();
        em.clear();

        /**
         * 회원이 등록한 키워드가 없을 경우 사이즈가 0인 리스트가 반환된다.
         */
        List<MemberKeyword> findMemberKeywords = memberKeywordRepository.findAllByMember(savedMember)
                .orElse(null);
        assertThat(findMemberKeywords.size()).isEqualTo(0);
    }
}