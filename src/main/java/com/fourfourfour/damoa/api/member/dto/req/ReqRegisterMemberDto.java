package com.fourfourfour.damoa.api.member.dto.req;

import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.*;
import java.time.LocalDate;

@NoArgsConstructor
@ToString(of = {"email", "password", "nickname", "gender", "birthDate", "job", "serviceTerm", "privacyTerm"})
@Getter
public class ReqRegisterMemberDto {

    @Pattern(regexp = "^[a-zA-Z0-9]([._-]?[a-zA-Z0-9])*@[a-zA-Z0-9]([-_.]?[a-zA-Z0-9])*.[a-zA-Z]$", message = "이메일을 올바르게 작성해주세요.")
    private String email;

    @Pattern(regexp = "^((?=.*[a-z])(?=.*\\d)((?=.*\\W)|(?=.*[A-Z]))|(?=.*\\W)(?=.*[A-Z])((?=.*\\d)|(?=.*[a-z]))).{8,20}$", message = "비밀번호를 올바르게 작성해주세요.")
    private String password;

    @Pattern(regexp = "^[0-9|a-z|A-Z|ㄱ-ㅎ|ㅏ-ㅣ|가-힣|\\s]{4,10}$", message = "닉네임을 올바르게 작성해주세요.")
    private String nickname;

    @Pattern(regexp = "^female|male$")
    private String gender;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate birthDate;

    @Pattern(regexp = "^회사원|학생|자영업자|전문직|기타$")
    private String job;

    @AssertTrue(message = "필수 동의 사항입니다.")
    private boolean serviceTerm;

    @AssertTrue(message = "필수 동의 사항입니다.")
    private boolean privacyTerm;

    @Builder
    public ReqRegisterMemberDto(String email, String password, String nickname, String gender, LocalDate birthDate, String job, boolean serviceTerm, boolean privacyTerm) {
        this.email = email;
        this.password = password;
        this.nickname = nickname;
        this.gender = gender;
        this.birthDate = birthDate;
        this.job = job;
        this.serviceTerm = serviceTerm;
        this.privacyTerm = privacyTerm;
    }
}
