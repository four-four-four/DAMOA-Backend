package com.fourfourfour.damoa.model.service.user;

import com.fourfourfour.damoa.model.dao.user.UserDao;
import com.fourfourfour.damoa.model.dto.user.UserDto;
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
    public boolean emailDuplicationCheck(String userEmail) {

        List<String> resultEmail = userDao.selectEmailByEmail(userEmail);

        if (resultEmail.isEmpty()) {
            return true;
        }
        else {
            return false;
        }
    }

    @Override
    public boolean nicknameDuplicationCheck(String userNickname) {

        List<String> resultNickname = userDao.selectByNickname(userNickname);

        if (resultNickname.isEmpty()) {
            return true;
        }
        else {
            return false;
        }
    }

}
