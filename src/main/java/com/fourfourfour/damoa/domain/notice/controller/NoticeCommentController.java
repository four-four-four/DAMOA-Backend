package com.fourfourfour.damoa.domain.notice.controller;

import com.fourfourfour.damoa.common.dto.response.BaseResponseDto;
import com.fourfourfour.damoa.common.util.AuthenticationUtil;
import com.fourfourfour.damoa.domain.notice.controller.dto.NoticeCommentRequestDto;
import com.fourfourfour.damoa.domain.notice.service.NoticeCommentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.CREATED;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/notices/{noticeSeq}/comments")
public class NoticeCommentController {

    private final NoticeCommentService noticeCommentService;

    @PreAuthorize("hasAnyAuthority('ROLE_MEMBER')")
    @ResponseStatus(CREATED)
    @PostMapping
    public BaseResponseDto<Void> commentRegister(@Validated @RequestBody NoticeCommentRequestDto.RegisterDto registerDto, @PathVariable Long noticeSeq) {
        Long memberSeq = AuthenticationUtil.getMemberSeq();

        noticeCommentService.register(registerDto.toServiceDto(noticeSeq), memberSeq);

        return BaseResponseDto.<Void>builder().build();
    }
}
