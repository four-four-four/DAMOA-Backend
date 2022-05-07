package com.fourfourfour.damoa.domain.notice.controller;

import com.fourfourfour.damoa.common.dto.response.BaseResponseDto;
import com.fourfourfour.damoa.common.util.AuthenticationUtil;
import com.fourfourfour.damoa.domain.notice.controller.dto.NoticeRequestDto;
import com.fourfourfour.damoa.domain.notice.service.NoticeService;
import com.fourfourfour.damoa.domain.notice.service.dto.NoticeResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

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
    public BaseResponseDto<Void> noticeRegister(@Valid @RequestBody NoticeRequestDto.RegisterDto registerDto) {
        Long memberSeq = authenticationUtil.getMemberSeq();

        noticeService.register(registerDto.toServiceDto(), memberSeq);

        return BaseResponseDto.<Void>builder().build();
    }

    @PreAuthorize("hasAnyAuthority('ROLE_MEMBER')")
    @ResponseStatus(OK)
    @GetMapping
    public BaseResponseDto<NoticeResponseDto.NoticeListPage> viewListPage(Pageable pageable) {
        NoticeResponseDto.NoticeListPage noticeListPage = noticeService.getPage(pageable);

        return BaseResponseDto.<NoticeResponseDto.NoticeListPage>builder()
                .data(noticeListPage)
                .build();
    }
}
