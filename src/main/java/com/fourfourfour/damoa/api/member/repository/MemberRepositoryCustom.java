package com.fourfourfour.damoa.api.member.repository;

import com.fourfourfour.damoa.api.member.dto.res.ResMemberDto;

import java.util.Optional;

public interface MemberRepositoryCustom {

    Optional<ResMemberDto> findResMemberDtoByEmail(String email);
}
