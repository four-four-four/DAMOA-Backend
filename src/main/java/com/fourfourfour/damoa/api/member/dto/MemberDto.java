package com.fourfourfour.damoa.api.member.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@ApiModel(value = "UserDto : 회원 정보", description = "회원 정보를 가집니다.")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MemberDto {

    @ApiModelProperty(value = "식별자")
    private Long id;

    @Email(message = "올바른 이메일이 아닙니다.")
    @ApiModelProperty(value = "이메일")
    private String email;

    @NotNull(message = "필수 사항입니다.")
    @ApiModelProperty(value = "패스워드")
    private String pw;

    @NotNull(message = "필수 사항입니다.")
    @ApiModelProperty(value = "닉네임")
    private String nickname;

    @ApiModelProperty(value = "성별")
    private String gender;

    @ApiModelProperty(value = "생년월일")
    private LocalDate birthDate;

    @ApiModelProperty(value = "직업")
    private String job;

    @AssertTrue(message = "필수 동의 사항입니다.")
    @ApiModelProperty(value = "서비스 약관 동의")
    private boolean serviceTerm;

    @AssertTrue(message = "필수 동의 사항입니다.")
    @ApiModelProperty(value = "개인정보 약관 동의")
    private boolean privacyTerm;

    @ApiModelProperty(value = "권한")
    private String role;

}
