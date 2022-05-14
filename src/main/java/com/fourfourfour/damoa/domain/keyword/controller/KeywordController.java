package com.fourfourfour.damoa.domain.keyword.controller;

import com.fourfourfour.damoa.common.constant.ErrorMessage;
import com.fourfourfour.damoa.common.dto.response.BaseResponseDto;
import com.fourfourfour.damoa.common.util.AuthenticationUtil;
import com.fourfourfour.damoa.domain.keyword.controller.dto.KeywordRegisterDto;
import com.fourfourfour.damoa.domain.keyword.service.BannedKeywordService;
import com.fourfourfour.damoa.domain.keyword.service.MemberKeywordService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/keywords")
public class KeywordController {

    private final MemberKeywordService memberKeywordService;

    private final BannedKeywordService bannedKeywordService;

    @PreAuthorize("hasAnyRole('ROLE_MEMBER')")
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public BaseResponseDto<Void> register(@Validated @RequestBody KeywordRegisterDto keywordRegisterDto) {

        String keywordName = keywordRegisterDto.getKeywordName();
        if (bannedKeywordService.isBanned(keywordName)) {
            throw new IllegalStateException(ErrorMessage.PATTERN);
        } else {
            Long findMemberSeq = AuthenticationUtil.getMemberSeq();
            memberKeywordService.register(keywordName, findMemberSeq);
        }

        return BaseResponseDto.<Void>builder()
                .build();
    }
}
