package com.fourfourfour.damoa.domain.member.service;

import com.fourfourfour.damoa.domain.member.service.dto.MemberDto;
import com.fourfourfour.damoa.domain.member.entity.Member;
import com.fourfourfour.damoa.domain.member.service.dto.MemberResponseDto;

public interface MemberService {

    Member register(MemberDto.RegisterDto registerDto);

    boolean isEmailDuplication(String email);

    boolean isNicknameDuplication(String nickname);

    MemberResponseDto.LoginInfo getLoginDtoByEmail(String email);

    void deleteAll();

    boolean login(String email, String password);
}
