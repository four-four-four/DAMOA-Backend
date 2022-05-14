package com.fourfourfour.damoa.domain.keyword.service;

import com.fourfourfour.damoa.domain.keyword.entity.BannedKeyword;
import com.fourfourfour.damoa.domain.keyword.repository.BannedKeywordRepository;
import com.fourfourfour.damoa.domain.keyword.repository.KeywordRepository;
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
class BannedKeywordServiceTest {

    @Autowired
    private EntityManager em;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private KeywordRepository keywordRepository;

    @Autowired
    private BannedKeywordRepository bannedKeywordRepository;

    @Autowired
    private BannedKeywordService bannedKeywordService;

    private String keywordName1;

    private BannedKeyword bannedKeyword1;

    @BeforeEach
    void setUp() {
        memberRepository.deleteAll();
        keywordRepository.deleteAll();
        bannedKeywordRepository.deleteAll();
        em.flush();
        em.clear();

        keywordName1 = "키워드1";

        bannedKeyword1 = BannedKeyword.builder()
                .name(keywordName1)
                .build();
    }

    @Test
    @DisplayName("차단 여부 확인 - 차단된 경우")
    void checkBannedKeywordTrue() {
        // given
        BannedKeyword savedBannedKeyword1 = bannedKeywordRepository.save(bannedKeyword1);
        em.flush();
        em.clear();

        // when
        boolean banned = bannedKeywordService.isBanned(savedBannedKeyword1.getName());

        // then
        assertThat(banned).isTrue();
    }
}