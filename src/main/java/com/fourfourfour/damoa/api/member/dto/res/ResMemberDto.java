package com.fourfourfour.damoa.api.member.dto.res;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
public class ResMemberDto {

    private final Long id;

    private final String email;

    private final String password;

    private final String nickname;

    private final String gender;

    private final LocalDate birthDate;

    private final String job;

    private final String role;

    @QueryProjection
    public ResMemberDto(Long id, String email, String password, String nickname, String gender, LocalDateTime birthDate, String job, String role) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.nickname = nickname;
        this.gender = gender;
        this.birthDate = birthDate == null ? null : birthDate.toLocalDate();
        this.job = job;
        this.role = role;
    }

}
