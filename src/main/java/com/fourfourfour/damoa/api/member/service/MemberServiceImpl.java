package com.fourfourfour.damoa.api.member.service;

import com.fourfourfour.damoa.api.member.dao.MemberDao;
import com.fourfourfour.damoa.api.member.dto.MemberDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MemberServiceImpl implements MemberService {

    @Autowired
    private MemberDao userDao;

    @Override
    public List<MemberDto> findAll() {
        return userDao.selectAll();
    }

    @Override
    public void removeAll() {
        userDao.deleteAll();
    }

    @Override
    public void register(MemberDto user) {
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
