package com.fourfourfour.damoa.domain.keyword.repository;

import com.fourfourfour.damoa.domain.keyword.entity.BannedKeyword;
import com.fourfourfour.damoa.domain.member.repository.MemberRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class BannedKeywordRepositoryTest {

    @Autowired
    private EntityManager em;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private KeywordRepository keywordRepository;

    @Autowired
    private BannedKeywordRepository bannedKeywordRepository;

    private BannedKeyword bannedKeyword1;

    @BeforeEach
    void setUp() {
        memberRepository.deleteAll();
        keywordRepository.deleteAll();
        bannedKeywordRepository.deleteAll();
        em.flush();
        em.clear();

        bannedKeyword1 = BannedKeyword.builder()
                .name("차단 키워드1")
                .build();
    }

    @Test
    @DisplayName("차단 키워드 확인 - 차단된 경우")
    void checkBannedKeywordWhenBanned() {
        // given
        BannedKeyword savedBannedKeyword = bannedKeywordRepository.save(bannedKeyword1);
        em.flush();
        em.clear();

        // when
        boolean exists = bannedKeywordRepository.existsByName(savedBannedKeyword.getName());

        // then
        assertThat(exists).isTrue();
    }
}