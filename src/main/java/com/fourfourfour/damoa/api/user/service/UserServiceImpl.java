package com.fourfourfour.damoa.api.user.service;

import com.fourfourfour.damoa.api.user.dao.UserDao;
import com.fourfourfour.damoa.api.user.dto.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Override
    public List<UserDto> findAll() {
        return userDao.selectAll();
    }

    @Override
    public void removeAll() {
        userDao.deleteAll();
    }

    @Override
    public void register(UserDto user) {
        userDao.insertUser(user);
    }

    @Override
    public boolean isEmailDuplication(String userEmail) {
        return userDao.selectCountByEmail(userEmail) != 0;
    }

    @Override
    public boolean isNicknameDuplication(String userNickname) {
        return userDao.selectCountByNickname(userNickname) != 0;
    }

}