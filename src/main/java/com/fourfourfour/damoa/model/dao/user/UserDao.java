package com.fourfourfour.damoa.model.dao.user;

import com.fourfourfour.damoa.model.dto.user.UserDto;

import java.util.List;

public interface UserDao {

    List<UserDto> selectAll();
    void deleteAll();
    void insertUser(UserDto userDto);
    int selectEmailByEmail(String userEmail);
    int selectByNickname(String userNickName);
    UserDto selectByUserEmail(String userEmail);

}
