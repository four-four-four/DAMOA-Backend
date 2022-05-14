package com.fourfourfour.damoa.domain.keyword.repository;

import com.fourfourfour.damoa.domain.keyword.entity.Keyword;
import com.fourfourfour.damoa.domain.member.entity.Member;
import com.fourfourfour.damoa.domain.member.repository.MemberRepository;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.time.LocalDate;

@SpringBootTest
@Transactional
class KeywordRepositoryTest {

    @Autowired
    private EntityManager em;

    @Autowired
    private KeywordRepository keywordRepository;

    @Autowired
    private MemberRepository memberRepository;


    private Keyword keyword1, keyword2;

    private Member member1, member2;

    @BeforeEach
    void setUp() {
        memberRepository.deleteAll();
        keywordRepository.deleteAll();
        em.flush();
        em.clear();

        member1 = Member.builder()
                .email("test1@damoa.com")
                .password("Abcdefg1!")
                .nickname("testNickname1")
                .gender(Member.Gender.FEMALE)
                .birthDate(LocalDate.of(1997, 10, 11))
                .job("대학생")
                .role(Member.Role.MEMBER)
                .serviceTerm(true)
                .privacyTerm(true)
                .build();

        member2 = Member.builder()
                .email("test2@damoa.com")
                .password("Abcdefg1!")
                .nickname("testNickname2")
                .gender(Member.Gender.FEMALE)
                .birthDate(LocalDate.of(1995, 10, 11))
                .job("고등학생")
                .role(Member.Role.MEMBER)
                .serviceTerm(true)
                .privacyTerm(true)
                .build();

        keyword1 = Keyword.builder()
                .name("키워드1")
                .build();

        keyword2 = Keyword.builder()
                .name("키워드2")
                .build();
    }
}