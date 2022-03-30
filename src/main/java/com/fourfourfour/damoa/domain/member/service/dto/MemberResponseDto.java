package com.fourfourfour.damoa.domain.member.service.dto;

import com.fourfourfour.damoa.domain.member.entity.Member;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
public class MemberResponseDto {

    @Getter
    @ToString(of = {"seq", "email", "nickname", "role"})
    public static class LoginInfo {

        private final Long seq;

        private final String email;

        private final String nickname;

        private final Member.Role role;

        private String jwtToken;

        @Builder
        public LoginInfo(Long seq, String email, String nickname, Member.Role role) {
            this.seq = seq;
            this.email = email;
            this.nickname = nickname;
            this.role = role;
        }

        public void addJwtToken(String jwtToken) {
            this.jwtToken = jwtToken;
        }
    }
}
