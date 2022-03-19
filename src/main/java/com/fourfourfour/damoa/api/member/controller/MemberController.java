package com.fourfourfour.damoa.api.member.controller;

import com.fourfourfour.damoa.api.member.dto.req.ReqLoginMemberDto;
import com.fourfourfour.damoa.api.member.dto.res.ResMemberDto;
import com.fourfourfour.damoa.common.message.ErrorMessage;
import com.fourfourfour.damoa.common.message.Message;
import com.fourfourfour.damoa.common.util.JwtTokenUtil;
import com.fourfourfour.damoa.common.dto.response.BaseResponseDto;
import com.fourfourfour.damoa.api.member.dto.req.ReqRegisterMemberDto;
import com.fourfourfour.damoa.api.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.*;

import static org.springframework.http.HttpStatus.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/members")
public class MemberController {

    private final MemberService memberService;

    private final PasswordEncoder passwordEncoder;

    @ResponseStatus(CREATED)
    @PostMapping
    public BaseResponseDto emailRegister(final @Validated @RequestBody ReqRegisterMemberDto reqRegisterMemberDto) {
        if (memberService.isEmailDuplication(reqRegisterMemberDto.getEmail())) {
            throw new IllegalArgumentException(Message.DUPLICATE_MEMBER_EMAIL);
        } else if (memberService.isNicknameDuplication(reqRegisterMemberDto.getNickname())) {
            throw new IllegalArgumentException(Message.DUPLICATE_MEMBER_NICKNAME);
        }

        memberService.register(reqRegisterMemberDto);

        return BaseResponseDto.builder()
                .data(Message.REGISTER_MEMBER)
                .data(null)
                .build();
    }

    @ResponseStatus(OK)
    @PostMapping("/login")
    protected BaseResponseDto login(@RequestBody ReqLoginMemberDto reqLoginMemberDto) {
        ResMemberDto resMemberDto = memberService.getResMemberDtoByEmail(reqLoginMemberDto.getEmail());
        if (resMemberDto == null) {
            throw new IllegalArgumentException(ErrorMessage.NULL_MEMBER);
        } else if (!passwordEncoder.matches(reqLoginMemberDto.getPassword(), resMemberDto.getPassword())) {
            throw new IllegalArgumentException(ErrorMessage.AUTHENTICATION_MEMBER);
        }

        Map<String, Object> data = new HashMap<>();
        String jwtToken = JwtTokenUtil.getToken(resMemberDto.getEmail());
        data.put("jwtToken", jwtToken);
        data.put("memberInfo", resMemberDto);

        return BaseResponseDto.builder()
                .message(Message.LOGIN)
                .data(data)
                .build();
    }

    @GetMapping("/email-duplicate-check/{email}")
    public ResponseEntity<BaseResponseDto> checkEmailDuplicate(@PathVariable String email) {
        String emailChk = "^[a-zA-Z0-9]([._-]?[a-zA-Z0-9])*@[a-zA-Z0-9]([-_.]?[a-zA-Z0-9])*.[a-zA-Z]$";
        if (!email.matches(emailChk)) {
            throw new IllegalArgumentException(ErrorMessage.PATTERN_MEMBER_EMAIL);
        }

        HttpStatus status = null;
        if (memberService.isEmailDuplication(email)) {
            status = OK;
        } else {
            status = NO_CONTENT;
        }

        return new ResponseEntity<>(BaseResponseDto.builder()
                .message(status == OK ? Message.DUPLICATE_MEMBER_EMAIL : Message.USABLE_MEMBER_EMAIL)
                .build(), status);
    }

    @GetMapping("/nickname-duplicate-check/{nickname}")
    public ResponseEntity<BaseResponseDto> checkNicknameDuplicate(@PathVariable String nickname) {
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

        return new ResponseEntity<>(BaseResponseDto.builder()
                .message(status == OK ? Message.DUPLICATE_MEMBER_NICKNAME : Message.USABLE_MEMBER_NICKNAME)
                .build(), status);
    }

}
