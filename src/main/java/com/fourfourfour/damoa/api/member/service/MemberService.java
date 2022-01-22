package com.fourfourfour.damoa.api.member.service;

import com.fourfourfour.damoa.api.member.dto.UserDto;

import java.util.List;

public interface UserService {

    List<UserDto> findAll();
    void removeAll();
    void register(UserDto user);
    boolean isEmailDuplication(String UserEmail);
    boolean isNicknameDuplication(String userNickname);

}
