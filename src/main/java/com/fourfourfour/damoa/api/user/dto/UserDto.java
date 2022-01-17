package com.fourfourfour.damoa.api.user.dto;

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
public class UserDto {

    @ApiModelProperty(value = "식별자")
    private Long userIdx;

    @Email(message = "올바른 이메일이 아닙니다.")
    @ApiModelProperty(value = "이메일")
    private String userEmail;

    @NotNull(message = "필수 사항입니다.")
    @ApiModelProperty(value = "패스워드")
    private String userPw;

    @NotNull(message = "필수 사항입니다.")
    @ApiModelProperty(value = "닉네임")
    private String userNickname;

    @ApiModelProperty(value = "성별")
    private String userGender;

    @ApiModelProperty(value = "생년월일")
    private LocalDate userBirthDate;

    @ApiModelProperty(value = "직업")
    private String userJob;

    @AssertTrue(message = "필수 동의 사항입니다.")
    @ApiModelProperty(value = "서비스 약관 동의")
    private boolean userServiceAgree;

    @AssertTrue(message = "필수 동의 사항입니다.")
    @ApiModelProperty(value = "개인정보 약관 동의")
    private boolean userPrivacyAgree;

    @ApiModelProperty(value = "위치정보 약관 동의")
    private boolean userLocationAgree;

    @ApiModelProperty(value = "프로모션 약관 동의")
    private boolean userPromotionAgree;

    @ApiModelProperty(value = "권한")
    private String role;

}
