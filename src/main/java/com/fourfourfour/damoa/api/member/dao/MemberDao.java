package com.fourfourfour.damoa.api.member.dao;

import com.fourfourfour.damoa.api.member.dto.MemberDto;

import java.util.List;

public interface MemberDao {

    List<MemberDto> selectAll();
    void deleteAll();
    void insertUser(MemberDto userDto);
    int selectCountByEmail(String userEmail);
    int selectCountByNickname(String userNickName);
    MemberDto selectUserByUserEmail(String userEmail);

}
