package com.fourfourfour.damoa.domain.keyword.service;

import com.fourfourfour.damoa.common.constant.ErrorMessage;
import com.fourfourfour.damoa.domain.keyword.entity.Keyword;
import com.fourfourfour.damoa.domain.keyword.entity.MemberKeyword;
import com.fourfourfour.damoa.domain.keyword.repository.KeywordRepository;
import com.fourfourfour.damoa.domain.keyword.repository.MemberKeywordRepository;
import com.fourfourfour.damoa.domain.member.entity.Member;
import com.fourfourfour.damoa.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberKeywordServiceImpl implements MemberKeywordService {

    private final MemberRepository memberRepository;

    private final KeywordRepository keywordRepository;

    private final MemberKeywordRepository memberKeywordRepository;

    @Transactional
    @Override
    public MemberKeyword register(String keywordName, Long memberSeq) {
        Member findMember = memberRepository.findBySeq(memberSeq)
                .orElseThrow(() -> new IllegalStateException(ErrorMessage.NULL_MEMBER));
        Keyword keyword = keywordRepository.findByName(keywordName)
                .orElse(keywordRepository.save(Keyword.builder()
                        .name(keywordName)
                        .build()));

        return memberKeywordRepository.save(MemberKeyword.builder()
                .member(findMember)
                .keyword(keyword)
                .build());
    }
}
