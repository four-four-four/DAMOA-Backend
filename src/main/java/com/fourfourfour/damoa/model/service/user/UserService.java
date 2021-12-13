package com.fourfourfour.damoa.model.service.user;

import com.fourfourfour.damoa.model.dto.user.UserDto;

import java.util.List;

public interface UserService {

    List<UserDto> findAll();
    void removeAll();
    void register(UserDto user);
    boolean emailDuplicationCheck(String UserEmail);
    boolean nicknameDuplicationCheck(String userNickname);

}
