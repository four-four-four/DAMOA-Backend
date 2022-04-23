package com.fourfourfour.damoa.domain.keyword.service;

import com.fourfourfour.damoa.domain.keyword.repository.KeywordRepository;
import com.fourfourfour.damoa.domain.keyword.repository.MemberKeywordRepository;
import com.fourfourfour.damoa.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class KeywordServiceImpl implements KeywordService {

    private final MemberKeywordRepository memberKeywordRepository;

    private final MemberRepository memberRepository;

    private final KeywordRepository keywordRepository;
}
