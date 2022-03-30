package com.fourfourfour.damoa.api.member.service;

import com.fourfourfour.damoa.api.member.service.dto.MemberDto;
import com.fourfourfour.damoa.api.member.entity.Member;
import com.fourfourfour.damoa.api.member.service.dto.MemberResponseDto;

public interface MemberService {

    Member register(MemberDto.RegisterDto registerDto);

    boolean isEmailDuplication(String email);

    boolean isNicknameDuplication(String nickname);

    MemberResponseDto.LoginInfo getLoginDtoByEmail(String email);

    void deleteAll();

    boolean login(String email, String password);
}
