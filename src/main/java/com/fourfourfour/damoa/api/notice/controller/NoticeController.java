package com.fourfourfour.damoa.api.notice.controller;

import com.fourfourfour.damoa.api.notice.dto.req.ReqNoticeDto;
import com.fourfourfour.damoa.api.notice.service.NoticeService;
import com.fourfourfour.damoa.common.auth.PrincipalDetails;
import com.fourfourfour.damoa.common.dto.response.BaseResponseDto;
import com.fourfourfour.damoa.common.message.Message;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;


import javax.validation.Valid;

import static org.springframework.http.HttpStatus.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/notice")
public class NoticeController {

    private final NoticeService noticeService;

    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN')")
    @ResponseStatus(CREATED)
    @PostMapping
    public BaseResponseDto noticeRegister(@Valid @RequestBody ReqNoticeDto reqNoticeDto, Authentication authentication) {

        Long memberSeq = ((PrincipalDetails) authentication.getDetails()).getMember().getSeq();

        noticeService.register(reqNoticeDto, memberSeq);

        return BaseResponseDto.builder()
                .message(Message.REGISTER_NOTICE)
                .build();
    }
}
