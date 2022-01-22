package com.fourfourfour.damoa.api.member.dao;

import com.fourfourfour.damoa.api.member.dto.UserDto;

import java.util.List;

public interface UserDao {

    List<UserDto> selectAll();
    void deleteAll();
    void insertUser(UserDto userDto);
    int selectCountByEmail(String userEmail);
    int selectCountByNickname(String userNickName);
    UserDto selectUserByUserEmail(String userEmail);

}
