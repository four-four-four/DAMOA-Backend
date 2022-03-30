package com.fourfourfour.damoa.domain.member.controller.dto;

import com.fourfourfour.damoa.domain.member.entity.Member;
import com.fourfourfour.damoa.domain.member.service.dto.MemberDto;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.Pattern;
import java.time.LocalDate;

@Getter
public class MemberRequestDto {

    @Getter
    @ToString(of = {"email", "password", "nickname", "gender", "birthDate", "job", "serviceTerm", "privacyTerm"})
    @NoArgsConstructor
    public static class RegisterDto {

        @Pattern(regexp = "^[a-zA-Z0-9]([._-]?[a-zA-Z0-9])*@[a-zA-Z0-9]([-_.]?[a-zA-Z0-9])*.[a-zA-Z]$", message = "{pattern.member.email}")
        private String email;

        @Pattern(regexp = "^((?=.*[a-z])(?=.*\\d)((?=.*\\W)|(?=.*[A-Z]))|(?=.*\\W)(?=.*[A-Z])((?=.*\\d)|(?=.*[a-z]))).{8,20}$", message = "{pattern.member.password}")
        private String password;

        @Pattern(regexp = "^[0-9|a-z|A-Z|ㄱ-ㅎ|ㅏ-ㅣ|가-힣|\\s]{4,10}$", message = "{pattern.member.nickname}")
        private String nickname;

        @Pattern(regexp = "^female|male$")
        private String gender;

        @DateTimeFormat(pattern = "yyyy-MM-dd")
        private LocalDate birthDate;

        @Pattern(regexp = "^회사원|학생|자영업자|전문직|기타$")
        private String job;

        @AssertTrue(message = "{pattern.member.term}")
        private boolean serviceTerm;

        @AssertTrue(message = "{pattern.member.term}")
        private boolean privacyTerm;

        @Builder
        public RegisterDto(String email, String password, String nickname, String gender, LocalDate birthDate, String job, boolean serviceTerm, boolean privacyTerm) {
            this.email = email;
            this.password = password;
            this.nickname = nickname;
            this.gender = gender;
            this.birthDate = birthDate;
            this.job = job;
            this.serviceTerm = serviceTerm;
            this.privacyTerm = privacyTerm;
        }

        public MemberDto.RegisterDto toServiceDto() {
            return MemberDto.RegisterDto.builder()
                    .email(email)
                    .password(password)
                    .nickname(nickname)
                    .gender(gender != null ? Member.Gender.valueOf(gender.toUpperCase()) : null)
                    .birthDate(birthDate)
                    .job(job)
                    .serviceTerm(serviceTerm)
                    .privacyTerm(privacyTerm)
                    .build();
        }
    }

    @Getter
    @ToString(of = {"email", "password"})
    public static class LoginDto {

        @Pattern(regexp = "^[a-zA-Z0-9]([._-]?[a-zA-Z0-9])*@[a-zA-Z0-9]([-_.]?[a-zA-Z0-9])*.[a-zA-Z]$", message = "{pattern.member.email}")
        private String email;

        private String password;
    }
}
