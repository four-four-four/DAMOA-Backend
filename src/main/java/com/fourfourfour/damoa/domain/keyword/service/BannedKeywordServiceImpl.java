package com.fourfourfour.damoa.domain.keyword.service;

import com.fourfourfour.damoa.domain.keyword.repository.BannedKeywordRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BannedKeywordServiceImpl implements BannedKeywordService {

    private final BannedKeywordRepository bannedKeywordRepository;

    @Override
    public boolean isBanned(String keywordName) {
        return bannedKeywordRepository.existsByName(keywordName);
    }
}
