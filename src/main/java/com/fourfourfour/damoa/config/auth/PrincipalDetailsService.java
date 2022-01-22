package com.fourfourfour.damoa.config.auth;

import com.fourfourfour.damoa.api.member.dao.MemberDao;
import com.fourfourfour.damoa.api.member.dto.MemberDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PrincipalDetailsService implements UserDetailsService {

    private final MemberDao userDao;

    @Override
    public UserDetails loadUserByUsername(String memberEmail) throws UsernameNotFoundException {
        System.out.println("PrincipalDetailsService의 loadUserByUsername()");
        System.out.println("PrincipalDetailsService memberEmail : " + memberEmail);
        MemberDto memberDto = userDao.selectUserByUserEmail(memberEmail);
        System.out.println("PrincipalDetailsService memberDto : " + memberDto);
        return new PrincipalDetails(memberDto);
    }

}
