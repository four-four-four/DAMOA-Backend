package com.fourfourfour.damoa.domain.keyword.service;

import com.fourfourfour.damoa.common.constant.ErrorMessage;
import com.fourfourfour.damoa.domain.keyword.entity.MemberKeyword;
import com.fourfourfour.damoa.domain.keyword.repository.KeywordRepository;
import com.fourfourfour.damoa.domain.keyword.repository.MemberKeywordRepository;
import com.fourfourfour.damoa.domain.keyword.service.dto.KeywordDto;
import com.fourfourfour.damoa.domain.member.entity.Member;
import com.fourfourfour.damoa.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class KeywordServiceImpl implements KeywordService {

    private final MemberKeywordRepository memberKeywordRepository;

    private final MemberRepository memberRepository;

    private final KeywordRepository keywordRepository;

    @Override
    public List<KeywordDto.BasicDto> keywordBasicList(Long memberSeq) {
        Member findMember = memberRepository.findBySeq(memberSeq)
                .orElseThrow(() -> new IllegalArgumentException(ErrorMessage.NULL_MEMBER));

        List<MemberKeyword> memberKeywords = memberKeywordRepository.findAllByMember(findMember)
                .orElse(null);

        if (memberKeywords == null || memberKeywords.size() == 0)
            return null;
        else
            return memberKeywords.stream()
                    .map(memberKeyword -> memberKeyword.getKeyword().toBasicDto())
                    .collect(Collectors.toList());
    }

    @Transactional
    @Override
    public void deleteAll() {
        keywordRepository.deleteAll();
    }
}
