package com.fourfourfour.damoa.domain.keyword.service;

import com.fourfourfour.damoa.domain.keyword.service.dto.KeywordDto;

import java.util.List;

public interface KeywordService {

    List<KeywordDto.BasicDto> keywordBasicList(Long memberSeq);

    void deleteAll();
}
