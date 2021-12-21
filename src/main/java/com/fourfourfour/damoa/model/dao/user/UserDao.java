package com.fourfourfour.damoa.model.dao.user;

import com.fourfourfour.damoa.model.dto.user.UserDto;

import java.util.List;

public interface UserDao {

    List<UserDto> selectAll();
    void deleteAll();
    void insertUser(UserDto userDto);
    String selectEmailByEmail(String userEmail);
    String selectByNickname(String userNickName);
    UserDto selectByUserEmail(String userEmail);
    UserDto selectUser(long integer);

}
