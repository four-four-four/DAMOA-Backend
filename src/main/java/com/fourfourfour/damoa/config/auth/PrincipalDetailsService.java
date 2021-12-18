package com.fourfourfour.damoa.config.auth;

import com.fourfourfour.damoa.model.dao.user.UserDao;
import com.fourfourfour.damoa.model.dto.user.UserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PrincipalDetailsService implements UserDetailsService {

    private final UserDao userDao;

    @Override
    public UserDetails loadUserByUsername(String userEmail) throws UsernameNotFoundException {
        System.out.println("PrincipalDetailsService의 loadUserByUsername()");
        System.out.println("PrincipalDetailsService userEmail : " + userEmail);
        UserDto user = userDao.selectByUserEmail(userEmail);
        System.out.println("PrincipalDetailsService user : " + user);
        return new PrincipalDetails(user);
    }

}
