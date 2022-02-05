package com.fourfourfour.damoa.config.auth;

import com.fourfourfour.damoa.api.member.entity.Member;
import com.fourfourfour.damoa.api.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PrincipalDetailsService implements UserDetailsService {

    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        System.out.println("PrincipalDetailsService의 loadUserByUsername()");
        System.out.println("PrincipalDetailsService email : " + email);
        Member member = memberRepository.findByEmail(email);
        System.out.println("PrincipalDetailsService member : " + member);
        return new PrincipalDetails(member);
    }

}
