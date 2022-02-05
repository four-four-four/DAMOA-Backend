package com.fourfourfour.damoa.api.member.service;

import com.fourfourfour.damoa.api.member.dto.req.ReqRegisterMemberDto;
import com.fourfourfour.damoa.api.member.dto.res.ResMemberDto;
import com.fourfourfour.damoa.api.member.entity.Member;

public interface MemberService {

    Member register(ReqRegisterMemberDto memberDto);

    boolean isEmailDuplication(String email);

    boolean isNicknameDuplication(String nickname);

    ResMemberDto getResMemberDtoByEmail(String email);

}
