package com.fourfourfour.damoa.config.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fourfourfour.damoa.config.auth.PrincipalDetails;
import com.fourfourfour.damoa.model.dao.user.UserDao;
import com.fourfourfour.damoa.model.dto.user.UserDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * spring security는 filter를 가지는데, 그 중 BasicAuthenticationFilter가 있다.
 * 권한이나 인증이 필요한 특정 주소를 요청하면 이 필터를 거치게 된다.
 * 권한이나 인증이 필요하지 않으면 이 필터를 거치지 않는다.
 */
public class JwtAuthorizationFilter extends BasicAuthenticationFilter {

    private static final Logger logger = LoggerFactory.getLogger(JwtAuthorizationFilter.class);

    private final String jwtKey;
    private final long tokenValidityInMilliseconds;

    private UserDao userDao;

    private JwtProperties jwtProperties;

    public JwtAuthorizationFilter(
            AuthenticationManager authenticationManager,
            UserDao userDao,
            JwtProperties jwtProperties,
            @Value("${jwt.key}") String jwtKey,
            @Value("${jwt.token-validity-in-seconds}") long tokenValidityInMilliseconds
            ) {
        super(authenticationManager);
        this.userDao = userDao;
        this.jwtProperties = jwtProperties;
        this.jwtKey = jwtKey;
        this.tokenValidityInMilliseconds = tokenValidityInMilliseconds * 1000;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        System.out.println("인증이나 권한이 필요한 주소 요청이 됨");

        String jwtHeader = request.getHeader(jwtProperties.getHEADER()); // Authorization
        System.out.println("jwtHeader : " + jwtHeader);

        if (jwtHeader == null || !jwtHeader.startsWith(jwtProperties.getPREFIX())) {
            chain.doFilter(request, response);
            return;
        }

        /**
         * JWT 토큰을 검증해서 정상적인 사용자인지 확인
         */
        String jwtToken = jwtHeader.replace(jwtProperties.getPREFIX(), "");
        String userEmail = JWT.require(Algorithm.HMAC512(jwtKey)).build().verify(jwtToken).getClaim(jwtProperties.getCLAIM()).asString();

        if (userEmail != null) {
            System.out.println("userEmail 정상 : " + userEmail);
            UserDto user = userDao.selectByUserEmail(userEmail);

            PrincipalDetails principalDetails = new PrincipalDetails(user);
            System.out.println("principalDetails : " + principalDetails.getUser().getUserEmail());

            /**
             * Jwt 토큰 서명을 통해서 서명이 정상이면 Authentication 객체륾 만들어준다.
             */
            Authentication authentication =
                    new UsernamePasswordAuthenticationToken(principalDetails, jwtToken, principalDetails.getAuthorities());

            /**
             * security session(SecurityContextHolder.getContext()) 에 접근해서 Authentication 객체 저장
             */
            SecurityContextHolder.getContext().setAuthentication(authentication);
            logger.debug("Security Context에 '{}' 인증 정보를 저장했습니다, uri: {}", authentication.getName(), ((HttpServletRequest) request).getRequestURI());
            chain.doFilter(request, response);
        }

    }
}
