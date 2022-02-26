package com.fourfourfour.damoa.api.member.controller;

import com.fourfourfour.damoa.api.member.dto.req.ReqLoginMemberDto;
import com.fourfourfour.damoa.api.member.dto.res.ResMemberDto;
import com.fourfourfour.damoa.common.util.JwtTokenUtil;
import com.fourfourfour.damoa.common.util.LogUtil;
import com.fourfourfour.damoa.common.dto.response.BaseResponseDto;
import com.fourfourfour.damoa.api.member.dto.req.ReqRegisterMemberDto;
import com.fourfourfour.damoa.api.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.*;

import static org.springframework.http.HttpStatus.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/members")
public class MemberController {

    private final MemberService memberService;

    private final PasswordEncoder passwordEncoder;

    @PostMapping("/email-register")
    public BaseResponseDto emailRegister(final @Valid @RequestBody ReqRegisterMemberDto reqRegisterMemberDto, Errors errors) {
        log.info(LogUtil.getClassAndMethodName());

        Integer status = null;
        Map<String, Object> responseData = new HashMap<>();

        // reqRegisterMemberDto 객체 안에 값이 제대로 들어왔는지 확인
        if (errors.hasErrors()) {
            status = BAD_REQUEST.value();
            if (errors.hasFieldErrors()) {
                responseData.put("field", errors.getFieldError().getField());
                responseData.put("message", errors.getFieldError().getDefaultMessage());
            } else if (errors.hasGlobalErrors()) {
                responseData.put("message", "global error");
            }
        }

        // 이메일 중복 확인
        else if(memberService.isEmailDuplication(reqRegisterMemberDto.getEmail())) {
            status = CONFLICT.value();
            responseData.put("message", "사용중인 이메일입니다.");
        }

        // 닉네임 중복 확인
        else if(memberService.isNicknameDuplication(reqRegisterMemberDto.getNickname())) {
            status = CONFLICT.value();
            responseData.put("message", "사용중인 닉네임입니다.");
        }

        else {
            try {
                memberService.register(reqRegisterMemberDto);
                status = CREATED.value();
                responseData.put("message", "회원가입 되었습니다.");
            } catch (Exception e) {
                log.error("Server Error");
                e.printStackTrace();

                status = INTERNAL_SERVER_ERROR.value();
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
        log.info(LogUtil.getClassAndMethodName());

        Integer status = null;
        Map<String, Object> responseData = new HashMap<>();

        ResMemberDto resMemberDto = memberService.getResMemberDtoByEmail(reqLoginMemberDto.getEmail());
        if (resMemberDto == null) {
            status = NO_CONTENT.value();
            responseData.put("message", "존재하지 않는 회원입니다.");
        } else if (!passwordEncoder.matches(reqLoginMemberDto.getPassword(), resMemberDto.getPassword())) {
            status = UNAUTHORIZED.value();
            responseData.put("message", "아이디 또는 비밀번호가 틀렸습니다.");
        } else {
            String jwtToken = JwtTokenUtil.getToken(resMemberDto.getEmail());

            status = OK.value();
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
        log.info(LogUtil.getClassAndMethodName());

        Integer status = null;
        Map<String, Object> responseData = new HashMap<>();

        String emailChk = "^[a-zA-Z0-9]([._-]?[a-zA-Z0-9])*@[a-zA-Z0-9]([-_.]?[a-zA-Z0-9])*.[a-zA-Z]$";

        if (!memberEmail.matches(emailChk)) {
            status = BAD_REQUEST.value();
            responseData.put("message", "이메일을 올바르게 작성해주세요.");
        } else if (memberService.isEmailDuplication(memberEmail)) {
            status = OK.value();
            responseData.put("message", "사용중인 이메일입니다.");
        } else {
            status = NO_CONTENT.value();
        }

        return BaseResponseDto.builder()
                .status(status)
                .data(responseData)
                .build();
    }

    @GetMapping("/nickname/{memberNickname}/exists")
    public BaseResponseDto checkNicknameDuplicate(@PathVariable String memberNickname) {
        log.info(LogUtil.getClassAndMethodName());

        Integer status = null;
        Map<String, Object> responseData = new HashMap<>();

        String nicknameChk = "^[0-9|a-z|A-Z|가-힣|\\s]{4,10}$";

        if (!memberNickname.matches(nicknameChk)) {
            status = BAD_REQUEST.value();
            responseData.put("message", "닉네임을 올바르게 작성해주세요.");
        } else if (memberService.isNicknameDuplication(memberNickname)) {
            status = OK.value();
            responseData.put("message", "사용중인 닉네임입니다.");
        } else {
            status = NO_CONTENT.value();
        }

        return BaseResponseDto.builder()
                .status(status)
                .data(responseData)
                .build();
    }

}
