package com.fourfourfour.damoa.api.member.controller;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fourfourfour.damoa.config.auth.PrincipalDetails;
import com.fourfourfour.damoa.config.jwt.JwtProperties;
import com.fourfourfour.damoa.common.dto.response.BasicResponseDto;
import com.fourfourfour.damoa.api.member.dto.MemberDto;
import com.fourfourfour.damoa.api.member.service.MemberService;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.*;

// http://localhost:9999/swagger-ui/index.html
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/members")
public class MemberController {

    private final MemberService memberService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final JwtProperties jwtProperties;

    @Operation(summary = "all member find", description = "전체 회원 정보 조회")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "전체 회원 정보 조회 성공"),
            @ApiResponse(responseCode = "400", description = "잘못된 요청"),
            @ApiResponse(responseCode = "404", description = "회원 정보를 찾을 수 없음"),
            @ApiResponse(responseCode = "500", description = "서버 장애 발생")
    })
    @ApiOperation(value="전체 회원 정보를 반환합니다.", response = BasicResponseDto.class)
    @GetMapping("/all")
    public ResponseEntity findAll() {
        BasicResponseDto response;

        /*
            파라미터가 있다면 확인
            서비스 메서드 호출
            파라미터 확인 or 서비스 메서드 호출 결과에 따라 응답
        */

        List<MemberDto> memberList = memberService.findAll();

        // 회원 정보가 있는 경우
        if (memberList != null) {
            response = BasicResponseDto.builder()
                    .status(HttpStatus.OK.value())
                    .data(memberList)
                    .build();
            return ResponseEntity.status(HttpStatus.OK).body(response);
        }
        // 회원 정보가 없는 경우
        else {
            response = BasicResponseDto.builder()
                    .status(HttpStatus.NO_CONTENT.value())
                    .data("회원 정보가 없습니다.")
                    .build();
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(response);
        }

    }

    @Operation(summary = "all member remove", description = "전체 회원 정보 삭제")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "전체 회원 정보 삭제 성공"),
            @ApiResponse(responseCode = "400", description = "잘못된 요청"),
            @ApiResponse(responseCode = "404", description = "회원 정보를 찾을 수 없음"),
            @ApiResponse(responseCode = "500", description = "서버 장애 발생")
    })
    @ApiOperation(value="전체 회원 정보를 삭제합니다.", response = BasicResponseDto.class)
    @DeleteMapping("/all")
    public ResponseEntity removeAll() {
        BasicResponseDto response;

        /*
            파라미터가 있다면 확인
            서비스 메서드 호출
            파라미터 확인 or 서비스 메서드 호출 결과에 따라 응답
        */

        try {
            // 삭제 요청이 성공한 경우
            memberService.removeAll();
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (Exception e) {
            response = BasicResponseDto.builder()
                    .status(HttpStatus.BAD_REQUEST.value())
                    .data("잘못된 요청입니다.")
                    .build();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }

    }

    @ApiOperation(value="회원 가입", response = BasicResponseDto.class)
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "회원 가입 성공"),
            @ApiResponse(responseCode = "400", description = "잘못된 요청"),
            @ApiResponse(responseCode = "404", description = "회원 정보를 찾을 수 없음"),
            @ApiResponse(responseCode = "500", description = "서버 장애 발생")
    })
    @PostMapping("/emailRegister")
    public ResponseEntity register(final @Valid @RequestBody MemberDto memberDto, Errors errors) {
        BasicResponseDto response;

        // memberDto 객체 안에 값이 제대로 들어왔는지 확인
        if (errors.hasErrors()) {
            response = BasicResponseDto.builder()
                    .status(HttpStatus.BAD_REQUEST.value())
                    .data(errors)
                    .build();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }

        // 이메일 중복 확인
        if(memberService.isEmailDuplication(memberDto.getEmail())) {
            response = BasicResponseDto.builder()
                    .status(HttpStatus.CONFLICT.value())
                    .data("사용중인 이메일입니다.")
                    .build();
            return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
        }

        // 닉네임 중복 확인
        if(memberService.isNicknameDuplication(memberDto.getNickname())) {
            response = BasicResponseDto.builder()
                    .status(HttpStatus.CONFLICT.value())
                    .data("사용중인 닉네임입니다.")
                    .build();
            return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
        }

        try {
            System.out.println(memberDto);
            memberDto.setPw(passwordEncoder.encode(memberDto.getPw()));
            memberDto.setRole("ROLE_member");
            memberService.register(memberDto);
            response = BasicResponseDto.builder()
                    .status(HttpStatus.CREATED.value())
                    .data("회원가입 되었습니다.")
                    .build();
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception e) {
            response = BasicResponseDto.builder()
                    .status(HttpStatus.BAD_REQUEST.value())
                    .data("잘못된 요청입니다.")
                    .build();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    @ApiOperation(value="로그인", response = BasicResponseDto.class)
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "로그인 성공 및 토큰 발행"),
            @ApiResponse(responseCode = "400", description = "잘못된 요청"),
            @ApiResponse(responseCode = "404", description = "회원 정보를 찾을 수 없음"),
            @ApiResponse(responseCode = "500", description = "서버 장애 발생")
    })
    @PostMapping("/login")
    protected ResponseEntity login(@RequestBody MemberDto member) {
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(member.getEmail(), member.getPw());

        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        PrincipalDetails principalDetails = (PrincipalDetails)authentication.getPrincipal();

        String jwtToken = JWT.create()
                .withSubject("token")
                .withExpiresAt(new Date(System.currentTimeMillis() + jwtProperties.getTokenValidityInMilliseconds()))
                .withClaim(jwtProperties.getCLAIM(), principalDetails.getMemberDto().getEmail())
                .sign(Algorithm.HMAC512(jwtProperties.getJwtKey()));

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(jwtProperties.getHEADER(), jwtProperties.getPREFIX() + jwtToken);

        Map<String, Object> responseData = new HashMap<>();
        responseData.put("jwtToken", jwtToken);
        responseData.put("role", principalDetails.getMemberDto().getRole());

        BasicResponseDto response = BasicResponseDto.builder()
                .status(200)
                .data(responseData)
                .build();

        return ResponseEntity.status(HttpStatus.OK).headers(httpHeaders).body(response);
    }

    @ApiOperation(value="이메일 중복 체크", response = BasicResponseDto.class)
    @ApiResponses({
            @ApiResponse(responseCode = "303", description = "이메일 중복"),
            @ApiResponse(responseCode = "400", description = "잘못된 요청"),
            @ApiResponse(responseCode = "404", description = "이메일이 중복되지 않습니다"),
            @ApiResponse(responseCode = "500", description = "서버 장애 발생")
    })
    @GetMapping("/email/{memberEmail}/exists")
    public ResponseEntity checkEmailDuplicate(@PathVariable String memberEmail) {
        BasicResponseDto response;

        // 이메일 중복 확인
//        if(memberService.isEmailDuplication(memberEmail)) {
        if(false) {
            response = BasicResponseDto.builder()
                    .status(HttpStatus.OK.value())
                    .data("사용중인 이메일입니다.")
                    .build();
            return ResponseEntity.status(HttpStatus.OK).body(response);
        }
        else {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
    }

    @ApiOperation(value="닉네임 중복 체크", response = BasicResponseDto.class)
    @ApiResponses({
            @ApiResponse(responseCode = "303", description = "닉네임 중복"),
            @ApiResponse(responseCode = "400", description = "잘못된 요청"),
            @ApiResponse(responseCode = "404", description = "닉네임이 중복되지 않습니다"),
            @ApiResponse(responseCode = "500", description = "서버 장애 발생")
    })
    @GetMapping("/nickname/{memberNickname}/exists")
    public ResponseEntity checkNicknameDuplicate(@PathVariable String memberNickname) {
        BasicResponseDto response;

        // 닉네임 중복 확인
//        if(memberService.isNicknameDuplication(memberNickname)) {
        if(false) {
            response = BasicResponseDto.builder()
                    .status(HttpStatus.OK.value())
                    .data("사용중인 닉네임입니다.")
                    .build();
            return ResponseEntity.status(HttpStatus.OK).body(response);
        }
        else {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
    }
}
