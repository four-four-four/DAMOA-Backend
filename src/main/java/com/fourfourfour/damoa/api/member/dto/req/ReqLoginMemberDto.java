package com.fourfourfour.damoa.api.member.dto.req;

import lombok.Getter;
import lombok.ToString;

@ToString(of = {"email", "password"})
@Getter
public class ReqLoginMemberDto {

    private String email;

    private String password;

}
