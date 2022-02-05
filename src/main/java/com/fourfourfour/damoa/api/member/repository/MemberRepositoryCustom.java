package com.fourfourfour.damoa.api.member.repository;

import com.fourfourfour.damoa.api.member.dto.res.ResMemberDto;

public interface MemberRepositoryCustom {

    ResMemberDto findResMemberDtoByEmail(String email);

}
