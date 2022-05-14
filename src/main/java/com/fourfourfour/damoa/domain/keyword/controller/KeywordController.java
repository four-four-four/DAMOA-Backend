package com.fourfourfour.damoa.domain.keyword.controller;

import com.fourfourfour.damoa.domain.keyword.service.BannedKeywordService;
import com.fourfourfour.damoa.domain.keyword.service.MemberKeywordService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/keywords")
public class KeywordController {

    private final MemberKeywordService memberKeywordService;

    private final BannedKeywordService bannedKeywordService;
}
