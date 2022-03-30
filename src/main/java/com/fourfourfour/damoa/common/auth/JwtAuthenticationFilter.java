package com.fourfourfour.damoa.common.auth;

import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fourfourfour.damoa.api.member.entity.Member;
import com.fourfourfour.damoa.api.member.repository.MemberRepository;
import com.fourfourfour.damoa.common.constant.ErrorMessage;
import com.fourfourfour.damoa.common.util.JwtTokenUtil;
import com.fourfourfour.damoa.common.util.ResponseBodyWriteUtil;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;

/**
 * spring security는 filter를 가지는데, 그 중 BasicAuthenticationFilter가 있다.
 * 권한이나 인증이 필요한 특정 주소를 요청하면 이 필터를 거치게 된다.
 * 권한이나 인증이 필요하지 않으면 이 필터를 거치지 않는다.
 */
public class JwtAuthenticationFilter extends BasicAuthenticationFilter {

    private final MemberRepository memberRepository;

    public JwtAuthenticationFilter(AuthenticationManager authenticationManager, MemberRepository memberRepository) {
        super(authenticationManager);
        this.memberRepository = memberRepository;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String header = request.getHeader(JwtTokenUtil.HEADER_STRING);

        if (header == null || !header.startsWith(JwtTokenUtil.TOKEN_PREFIX)) {
            filterChain.doFilter(request, response);
            return;
        }

        try {
            Authentication authentication = getAuthentication(request);
            SecurityContextHolder.getContext().setAuthentication(authentication);
        } catch (Exception ex) {
            ResponseBodyWriteUtil.sendError(request, response, ex);
            return;
        }

        filterChain.doFilter(request, response);
    }

    @Transactional(readOnly = true)
    public Authentication getAuthentication(HttpServletRequest request) {
        String token = request.getHeader(JwtTokenUtil.HEADER_STRING);

        if (token != null) {
            JWTVerifier verifier = JwtTokenUtil.getVerifier();
            JwtTokenUtil.handleError(token);
            DecodedJWT decodedJWT = verifier.verify(token.replace(JwtTokenUtil.TOKEN_PREFIX, ""));
            String email = decodedJWT.getSubject();

            if (email != null) {
                Member member = memberRepository.findByEmail(email)
                        .orElseThrow(() -> new IllegalArgumentException(ErrorMessage.NULL_MEMBER));

                if(member != null) {
                    PrincipalDetails customUserDetails = new PrincipalDetails(member);
                    customUserDetails.setAuthorities(Collections.singletonList(member.getRole()));
                    UsernamePasswordAuthenticationToken jwtAuthentication =
                            new UsernamePasswordAuthenticationToken(email, null, customUserDetails.getAuthorities());
                    jwtAuthentication.setDetails(customUserDetails);
                    return jwtAuthentication;
                }
            }

            return null;
        }

        return null;
    }
}
