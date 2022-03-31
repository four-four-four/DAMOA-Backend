package com.fourfourfour.damoa.domain.notice.controller;

import com.fourfourfour.damoa.common.dto.response.BaseResponseDto;
import com.fourfourfour.damoa.common.util.AuthenticationUtil;
import com.fourfourfour.damoa.domain.notice.controller.dto.NoticeCommentRequestDto;
import com.fourfourfour.damoa.domain.notice.service.NoticeCommentService;
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
@RequestMapping("/api/v1/notice/comment")
public class NoticeCommentController {

    private final NoticeCommentService noticeCommentService;

    private final AuthenticationUtil authenticationUtil;

    @PreAuthorize("hasAnyAuthority('ROLE_MEMBER')")
    @ResponseStatus(CREATED)
    @PostMapping
    public BaseResponseDto<?> commentRegister(@Valid @RequestBody NoticeCommentRequestDto.RegisterDto registerDto, Authentication authentication) {
        log.info("공지사항 댓글 등록 = {}", registerDto);

        Long memberSeq = authenticationUtil.getMemberSeq(authentication);

        noticeCommentService.register(registerDto.toServiceDto(), memberSeq);

        return BaseResponseDto.builder().build();
    }
}
