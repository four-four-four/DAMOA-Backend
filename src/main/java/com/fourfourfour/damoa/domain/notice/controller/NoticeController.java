package com.fourfourfour.damoa.domain.notice.controller;

import com.fourfourfour.damoa.domain.notice.controller.dto.NoticeRequestDto;
import com.fourfourfour.damoa.domain.notice.service.NoticeService;
import com.fourfourfour.damoa.common.dto.response.BaseResponseDto;
import com.fourfourfour.damoa.common.util.AuthenticationUtil;
import com.fourfourfour.damoa.domain.notice.service.dto.NoticeResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;


import javax.validation.Valid;

import static org.springframework.http.HttpStatus.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/notices")
public class NoticeController {

    private final NoticeService noticeService;

    private final AuthenticationUtil authenticationUtil;

    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN')")
    @ResponseStatus(CREATED)
    @PostMapping
    public BaseResponseDto<?> noticeRegister(@Valid @RequestBody NoticeRequestDto.RegisterDto registerDto, Authentication authentication) {

        log.info("공지사항 등록 = {}", registerDto);

        Long memberSeq = authenticationUtil.getMemberSeq(authentication);

        noticeService.register(registerDto.toServiceDto(), memberSeq);

        return BaseResponseDto.builder().build();
    }

    @PreAuthorize("hasAnyAuthority('ROLE_MEMBER')")
    @ResponseStatus(OK)
    @GetMapping
    public BaseResponseDto<NoticeResponseDto.NoticeListPage> viewListPage(Pageable pageable) {

        log.info("페이징 정보 = {}", pageable);

        NoticeResponseDto.NoticeListPage noticeListPage = noticeService.getPage(pageable);

        return BaseResponseDto.<NoticeResponseDto.NoticeListPage>builder()
                .data(noticeListPage)
                .build();
    }

    @PreAuthorize("hasAnyAuthority('ROLE_MEMBER')")
    @ResponseStatus(OK)
    @GetMapping("/{noticeSeq}")
    public BaseResponseDto<NoticeResponseDto.Detail> viewDetail(@PathVariable Long noticeSeq) {

        NoticeResponseDto.Detail noticeDetail = noticeService.getDetail(noticeSeq);

        return BaseResponseDto.<NoticeResponseDto.Detail>builder()
                .data(noticeDetail)
                .build();
    }
}
