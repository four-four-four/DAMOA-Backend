package com.fourfourfour.damoa.api.member.dto.req;

import lombok.Getter;
import lombok.ToString;

import javax.validation.constraints.*;
import java.time.LocalDate;

@ToString(of = {"email", "password", "nickname", "gender", "birthDate", "job", "serviceTerm", "privacyTerm", "role"})
@Getter
public class ReqRegisterMemberDto {

    @Pattern(regexp = "^[a-zA-Z0-9]([._-]?[a-zA-Z0-9])*@[a-zA-Z0-9]([-_.]?[a-zA-Z0-9])*.[a-zA-Z]$", message = "이메일을 올바르게 작성해주세요.")
    private String email;

    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*\\W)(?=\\S+$).{8,20}$", message = "비밀번호를 올바르게 작성해주세요.")
    private String password;

    @Pattern(regexp = "^[0-9|a-z|A-Z|ㄱ-ㅎ|ㅏ-ㅣ|가-힣|\\s]*$", message = "닉네임을 올바르게 작성해주세요.")
    private String nickname;

    private String gender;

    private LocalDate birthDate;

    private String job;

    @AssertTrue(message = "필수 동의 사항입니다.")
    private boolean serviceTerm;

    @AssertTrue(message = "필수 동의 사항입니다.")
    private boolean privacyTerm;

    private String role;

}
