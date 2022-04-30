package com.fourfourfour.damoa.domain.member.controller;

import com.fourfourfour.damoa.common.util.AuthenticationUtil;
import com.fourfourfour.damoa.domain.keyword.service.KeywordService;
import com.fourfourfour.damoa.domain.keyword.service.dto.KeywordDto;
import com.fourfourfour.damoa.domain.member.controller.dto.MemberRequestDto;
import com.fourfourfour.damoa.domain.member.service.dto.MemberResponseDto;
import com.fourfourfour.damoa.common.constant.ErrorMessage;
import com.fourfourfour.damoa.common.constant.Message;
import com.fourfourfour.damoa.common.util.JwtTokenUtil;
import com.fourfourfour.damoa.common.dto.response.BaseResponseDto;
import com.fourfourfour.damoa.domain.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/members")
public class MemberController {

    private final AuthenticationUtil authenticationUtil;

    private final MemberService memberService;

    private final KeywordService keywordService;

    @ResponseStatus(CREATED)
    @PostMapping
    public BaseResponseDto<?> emailRegister(final @Validated @RequestBody MemberRequestDto.RegisterDto registerDto) {
        log.info("회원가입 정보 = {}", registerDto);

        if (memberService.isEmailDuplication(registerDto.getEmail())) {
            throw new IllegalArgumentException(Message.DUPLICATE_MEMBER_EMAIL);
        } else if (memberService.isNicknameDuplication(registerDto.getNickname())) {
            throw new IllegalArgumentException(Message.DUPLICATE_MEMBER_NICKNAME);
        }

        memberService.register(registerDto.toServiceDto());

        return BaseResponseDto.builder().build();
    }

    @PostMapping("/login")
    protected ResponseEntity<BaseResponseDto<MemberResponseDto.LoginInfo>> login(@RequestBody MemberRequestDto.LoginDto loginDto) {
        log.info("로그인 정보 = {}", loginDto);

        HttpStatus status = null;
        MemberResponseDto.LoginInfo memberInfo = null;
        if (memberService.login(loginDto.getEmail(), loginDto.getPassword())) {
            memberInfo = memberService.getLoginDtoByEmail(loginDto.getEmail());
            memberInfo.addJwtToken(JwtTokenUtil.getToken(loginDto.getEmail()));
            status = OK;
        } else {
            status = NO_CONTENT;
        }

        return new ResponseEntity<>(BaseResponseDto.<MemberResponseDto.LoginInfo>builder()
                .data(memberInfo)
                .build(), status);
    }

    @GetMapping("/email-duplicate-check/{email}")
    public ResponseEntity<BaseResponseDto<Void>> checkEmailDuplicate(@PathVariable String email) {
        log.info("이메일 중복 확인 [{}]", email);

        String emailChk = "^[a-zA-Z0-9]([-_.]?[a-zA-Z0-9])*@[a-zA-Z0-9]([-_.]?[a-zA-Z0-9])*\\.[a-zA-Z]{2,3}$";
        if (!email.matches(emailChk)) {
            throw new IllegalArgumentException(ErrorMessage.PATTERN_MEMBER_EMAIL);
        }

        HttpStatus status = null;
        if (memberService.isEmailDuplication(email)) {
            status = OK;
        } else {
            status = NO_CONTENT;
        }

        return new ResponseEntity<>(BaseResponseDto.<Void>builder().build(), status);
    }

    @GetMapping("/nickname-duplicate-check/{nickname}")
    public ResponseEntity<BaseResponseDto<Void>> checkNicknameDuplicate(@PathVariable String nickname) {
        log.info("닉네임 중복 확인 [{}]", nickname);

        String nicknameChk = "^[0-9|a-z|A-Z|가-힣|\\s]{4,10}$";
        if (!nickname.matches(nicknameChk)) {
            throw new IllegalArgumentException(ErrorMessage.PATTERN_MEMBER_NICKNAME);
        }

        HttpStatus status = null;
        if (memberService.isNicknameDuplication(nickname)) {
            status = OK;
        } else {
            status = NO_CONTENT;
        }

        return new ResponseEntity<>(BaseResponseDto.<Void>builder().build(), status);
    }

    @PreAuthorize("hasAnyRole('ROLE_MEMBER')")
    @GetMapping("/{memberSeq}/keywords")
    public ResponseEntity<BaseResponseDto<List<KeywordDto.BasicDto>>> memberKeywordList(@PathVariable("memberSeq") Long memberSeq,
                                                                                        Authentication authentication) {

        authenticationUtil.verifyMemberSeq(authentication, memberSeq);

        List<KeywordDto.BasicDto> keywordBasicList = keywordService.keywordBasicList(memberSeq);
        HttpStatus status = keywordBasicList == null ? NO_CONTENT : OK;

        return new ResponseEntity<>(BaseResponseDto.<List<KeywordDto.BasicDto>>builder()
                .data(keywordBasicList)
                .build(), status);
    }
}
