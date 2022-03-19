package com.fourfourfour.damoa.api.member.dto.req;

import lombok.Getter;
import lombok.ToString;

import javax.validation.constraints.Pattern;

@ToString(of = {"email", "password"})
@Getter
public class ReqLoginMemberDto {

    @Pattern(regexp = "^[a-zA-Z0-9]([._-]?[a-zA-Z0-9])*@[a-zA-Z0-9]([-_.]?[a-zA-Z0-9])*.[a-zA-Z]$", message = "{pattern.member.email}")
    private String email;

    private String password;

}
