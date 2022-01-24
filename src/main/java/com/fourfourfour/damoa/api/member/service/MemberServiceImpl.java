package com.fourfourfour.damoa.api.member.service;

import com.fourfourfour.damoa.api.member.dao.MemberDao;
import com.fourfourfour.damoa.api.member.dto.MemberDto;
import com.fourfourfour.damoa.api.member.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MemberServiceImpl implements MemberService {

    @Autowired
    private MemberDao userDao;

    @Autowired
    private MemberRepository memberRepository;

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
    public boolean isEmailDuplication(String memberEmail) {
        return memberRepository.existsByEmail(memberEmail);
    }

    @Override
    public boolean isNicknameDuplication(String memberNickname) {
        return memberRepository.countByNickname(memberNickname) != 0;
    }

}
