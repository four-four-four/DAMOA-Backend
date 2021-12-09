package com.fourfourfour.damoa.model.dto.user;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDate;

@ApiModel(value = "UserDto : 회원 정보", description = "회원 정보를 가집니다.")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {

    @ApiModelProperty(value = "식별자")
    private Long userIdx;
    @NonNull
    @ApiModelProperty(value = "이메일")
    private String userEmail;
    @NonNull
    @ApiModelProperty(value = "패스워드")
    private String userPw;
    @NonNull
    @ApiModelProperty(value = "닉네임")
    private String userNickname;
    @ApiModelProperty(value = "성별")
    private String userGender;
    @ApiModelProperty(value = "생년월일")
    private LocalDate userBirthDate;
    @ApiModelProperty(value = "직업")
    private String userJob;
    @NonNull
    @ApiModelProperty(value = "서비스 약관 동의")
    private boolean userServiceAgree;
    @NonNull
    @ApiModelProperty(value = "개인정보 약관 동의")
    private boolean userPrivacyAgree;
    @ApiModelProperty(value = "위치정보 약관 동의")
    private boolean userLocationAgree;
    @ApiModelProperty(value = "프로모션 약관 동의")
    private boolean userPromotionAgree;

}
