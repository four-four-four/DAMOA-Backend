package com.fourfourfour.damoa.domain.keyword.service;

import com.fourfourfour.damoa.domain.keyword.entity.MemberKeyword;

public interface MemberKeywordService {

    MemberKeyword register(String keywordName, Long memberSeq);
}
