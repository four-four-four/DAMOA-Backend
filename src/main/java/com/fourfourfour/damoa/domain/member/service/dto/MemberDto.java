package com.fourfourfour.damoa.domain.member.service.dto;

import com.fourfourfour.damoa.domain.member.entity.Member;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDate;

@Getter
public class MemberDto {

    @Getter
    @ToString(of = {"email", "password", "nickname", "gender", "birthDate", "job", "serviceTerm"})
    public static class RegisterDto {

        private final String email;

        private final String password;

        private final String nickname;

        private final Member.Gender gender;

        private final LocalDate birthDate;

        private final String job;

        private final boolean serviceTerm;

        private final boolean privacyTerm;

        @Builder
        public RegisterDto(String email, String password, String nickname, Member.Gender gender, LocalDate birthDate, String job, boolean serviceTerm, boolean privacyTerm) {
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
}
