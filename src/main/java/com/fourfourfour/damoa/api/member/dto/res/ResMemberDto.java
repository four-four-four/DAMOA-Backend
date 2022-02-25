package com.fourfourfour.damoa.api.member.dto.res;

import com.fourfourfour.damoa.api.member.enums.Gender;
import com.fourfourfour.damoa.api.member.enums.Role;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;

import java.time.LocalDate;

@Getter
public class ResMemberDto {

    private final Long seq;

    private final String email;

    private final String password;

    private final String nickname;

    private final Gender gender;

    private final LocalDate birthDate;

    private final String job;

    private final Role role;

    @QueryProjection
    public ResMemberDto(Long seq, String email, String password, String nickname, Gender gender, LocalDate birthDate, String job, Role role) {
        this.seq = seq;
        this.email = email;
        this.password = password;
        this.nickname = nickname;
        this.gender = gender;
        this.birthDate = birthDate;
        this.job = job;
        this.role = role;
    }
}
