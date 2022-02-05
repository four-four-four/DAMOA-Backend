package com.fourfourfour.damoa.api.member.controller;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fourfourfour.damoa.api.member.dto.req.ReqLoginMemberDto;
import com.fourfourfour.damoa.api.member.dto.res.ResMemberDto;
import com.fourfourfour.damoa.config.jwt.JwtProperties;
import com.fourfourfour.damoa.common.dto.response.BaseResponseDto;
import com.fourfourfour.damoa.api.member.dto.req.ReqRegisterMemberDto;
import com.fourfourfour.damoa.api.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/members")
public class MemberController {

    private final MemberService memberService;

    private final JwtProperties jwtProperties;

    private final PasswordEncoder passwordEncoder;

    @PostMapping("/email-register")
    public BaseResponseDto emailRegister(final @Valid @RequestBody ReqRegisterMemberDto reqRegisterMemberDto, Errors errors) {
        Integer status = null;
        Map<String, Object> responseData = new HashMap<>();

        System.out.println("reqRegisterMemberDto = " + reqRegisterMemberDto);

        // reqRegisterMemberDto 객체 안에 값이 제대로 들어왔는지 확인
        if (errors.hasErrors()) {
            status = HttpStatus.BAD_REQUEST.value();
            if (errors.hasFieldErrors()) {
                responseData.put("field", errors.getFieldError().getField());
                responseData.put("message", errors.getFieldError().getDefaultMessage());
            } else if (errors.hasGlobalErrors()) {
                responseData.put("message", "global error");
            }
        }

        // 이메일 중복 확인
        else if(memberService.isEmailDuplication(reqRegisterMemberDto.getEmail())) {
            status = HttpStatus.CONFLICT.value();
            responseData.put("message", "사용중인 이메일입니다.");
        }

        // 닉네임 중복 확인
        else if(memberService.isNicknameDuplication(reqRegisterMemberDto.getNickname())) {
            status = HttpStatus.CONFLICT.value();
            responseData.put("message", "사용중인 닉네임입니다.");
        }

        else {
            try {
                memberService.register(reqRegisterMemberDto);
                status = HttpStatus.CREATED.value();
                responseData.put("message", "회원가입 되었습니다.");
            } catch (Exception e) {
                status = HttpStatus.INTERNAL_SERVER_ERROR.value();
                responseData.put("message", "요청을 수행할 수 없습니다.");
            }
        }

        return BaseResponseDto.builder()
                .status(status)
                .data(responseData)
                .build();
    }

    @PostMapping("/login")
    protected BaseResponseDto login(@RequestBody ReqLoginMemberDto reqLoginMemberDto) {
        Integer status = null;
        Map<String, Object> responseData = new HashMap<>();

        System.out.println("reqLoginMemberDto = " + reqLoginMemberDto);

        ResMemberDto resMemberDto = memberService.getResMemberDtoByEmail(reqLoginMemberDto.getEmail());
        if (resMemberDto == null) {
            status = HttpStatus.NO_CONTENT.value();
            responseData.put("message", "존재하지 않는 회원입니다.");
        } else if (!passwordEncoder.matches(reqLoginMemberDto.getPassword(), resMemberDto.getPassword())) {
            status = HttpStatus.UNAUTHORIZED.value();
            responseData.put("message", "아이디 또는 비밀번호가 틀렸습니다.");
        } else {
            String jwtToken = JWT.create()
                    .withSubject("token")
                    .withExpiresAt(new Date(System.currentTimeMillis() + jwtProperties.getTokenValidityInMilliseconds()))
                    .withClaim(jwtProperties.getCLAIM(), reqLoginMemberDto.getEmail())
                    .sign(Algorithm.HMAC512(jwtProperties.getJwtKey()));

            status = HttpStatus.OK.value();
            responseData.put("jwtToken", jwtToken);
            responseData.put("memberInfo", resMemberDto);
        }

        return BaseResponseDto.builder()
                .status(status)
                .data(responseData)
                .build();
    }

    @GetMapping("/email/{memberEmail}/exists")
    public BaseResponseDto checkEmailDuplicate(@PathVariable String memberEmail) {
        Integer status = null;
        Map<String, Object> responseData = new HashMap<>();

        System.out.println("memberEmail = " + memberEmail);

        String emailChk = "^[a-zA-Z0-9]([._-]?[a-zA-Z0-9])*@[a-zA-Z0-9]([-_.]?[a-zA-Z0-9])*.[a-zA-Z]$";

        if (!memberEmail.matches(emailChk)) {
            status = HttpStatus.BAD_REQUEST.value();
            responseData.put("message", "이메일을 올바르게 작성해주세요.");
        }
        else if (memberService.isEmailDuplication(memberEmail)) {
            status = HttpStatus.OK.value();
            responseData.put("message", "사용중인 이메일입니다.");
        }
        else {
            status = HttpStatus.NO_CONTENT.value();
        }

        return BaseResponseDto.builder()
                .status(status)
                .data(responseData)
                .build();
    }

    @GetMapping("/nickname/{memberNickname}/exists")
    public BaseResponseDto checkNicknameDuplicate(@PathVariable String memberNickname) {
        Integer status = null;
        Map<String, Object> responseData = new HashMap<>();

        // 닉네임 중복 확인
        if(memberService.isNicknameDuplication(memberNickname)) {
        String nickChk = "^[0-9|a-z|A-Z|가-힣|\\s]{4,10}$";

        if (!memberNickname.matches(nickChk)) {
            status = HttpStatus.BAD_REQUEST.value();
            responseData.put("message", "닉네임을 올바르게 작성해주세요.");
        }
            status = HttpStatus.OK.value();
            responseData.put("message", "사용중인 닉네임입니다.");
        } else {
            status = HttpStatus.NO_CONTENT.value();
            responseData.put("message", "사용하실 수 있는 이메일입니다.");
        }

        return BaseResponseDto.builder()
                .status(status)
                .data(responseData)
                .build();
    }

}
