package com.fourfourfour.damoa.api.member.service;

import com.fourfourfour.damoa.api.member.dto.MemberDto;

import java.util.List;

public interface MemberService {

    List<MemberDto> findAll();
    void removeAll();
    void register(MemberDto user);
    boolean isNicknameDuplication(String userNickname);
    boolean isEmailDuplication(String memberEmail);

}
