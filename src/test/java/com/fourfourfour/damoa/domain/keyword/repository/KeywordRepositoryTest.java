package com.fourfourfour.damoa.domain.keyword.repository;

import com.fourfourfour.damoa.domain.keyword.entity.Keyword;
import com.fourfourfour.damoa.domain.member.entity.Member;
import com.fourfourfour.damoa.domain.member.repository.MemberRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

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

    @Test
    @DisplayName("이름으로 키워드 엔티티 조회 - 데이터가 있는 경우")
    void findByName() {
        // given
        Keyword savedKeyword1 = keywordRepository.save(keyword1);
        em.flush();
        em.clear();

        // when
        Keyword findKeyword1 = keywordRepository.findByName(savedKeyword1.getName())
                .orElse(null);

        // then
        assertThat(findKeyword1.getSeq()).isEqualTo(savedKeyword1.getSeq());
        assertThat(findKeyword1.getName()).isEqualTo(savedKeyword1.getName());
    }

    @Test
    @DisplayName("이름으로 키워드 엔티티 조회 - 데이터가 없는 경우")
    void findByNameWhenNotExists() {
        // given

        // when
        Keyword findKeyword = keywordRepository.findByName("noName")
                .orElse(null);

        // then
        assertThat(findKeyword).isNull();
    }

    @Test
    @DisplayName("이름으로 키워드 엔티티 조회 - 데이터가 있지만 삭제 처리된 경우")
    void findByNameWhenDeleted() {
        // given
        keyword1.delete();
        Keyword deletedKeyword1 = keywordRepository.save(keyword1);
        em.flush();
        em.clear();

        // when
        Keyword findKeyword1 = keywordRepository.findByName(deletedKeyword1.getName())
                .orElse(null);

        // then
        assertThat(findKeyword1).isNull();
    }
}