package com.fourfourfour.damoa.api.member.dto.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;
import java.time.LocalDate;

@ApiModel(value = "UserDto : 회원 정보", description = "회원 정보를 가집니다.")
@NoArgsConstructor
@Getter
public class ReqMemberDto {

    @Pattern(regexp = "^[a-zA-Z0-9]([._-]?[a-zA-Z0-9])*@[a-zA-Z0-9]([-_.]?[a-zA-Z0-9])*.[a-zA-Z]$", message = "이메일을 올바르게 작성해주세요.")
    @ApiModelProperty(value = "이메일")
    private String email;

    @NotNull(message = "필수 사항입니다.")
    @ApiModelProperty(value = "패스워드")
    private String password;

    @NotNull(message = "필수 사항입니다.")
    @Size(min = 4, max = 10, message = "닉네임은 4자 이상 10자 이하로 입력해주세요")
    @Pattern(regexp = "^[^ㄱ-ㅎ|ㅏ-ㅣ]*$", message = "닉네임에 자음 또는 모음을 사용할 수 없습니다.") // 자음 - 모음이 들어가면 안됨
    @Pattern(regexp = "^[0-9|a-z|A-Z|ㄱ-ㅎ|ㅏ-ㅣ|가-힣|\\s]*$", message = "닉네임에 특수문자를 사용할 수 없습니다.") // [] 안에 들어간 문자 빼고는 안됨
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
