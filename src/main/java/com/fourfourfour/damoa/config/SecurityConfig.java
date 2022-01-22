package com.fourfourfour.damoa.config;

import com.fourfourfour.damoa.config.jwt.JwtAuthorizationFilter;
import com.fourfourfour.damoa.config.jwt.JwtProperties;
import com.fourfourfour.damoa.api.member.dao.MemberDao;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.filter.CorsFilter;

@Configuration
/**
 * spring security filter(SecurityConfig 클래스)가 스프링 필터체인에 등록이 된다.
 */
@EnableWebSecurity
/**
 * secured 애노테이션 활성화, preAuthorize 애노테이션 활성화
 */
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final CorsFilter corsFilter;
    private final MemberDao userDao;
    private final JwtProperties jwtProperties;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                /**
                 * csrf 비활성화
                 * https://velog.io/@woohobi/Spring-security-csrf%EB%9E%80
                 */
                .csrf().disable()

                /**
                 * 세션 비활성화
                 */
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()

                /**
                 * 필터 추가
                 */
                .addFilter(corsFilter)

                .formLogin().disable()

                /**
                 * 토큰 방식을 사용하므로
                 * 기존의 HTTP 로그인 방식(ID, PW를 보내는 방식) 비활성화
                 */
                .httpBasic().disable()

                /**
                 * 로그인 필터 등록
                 */
                .addFilterBefore(
                        new JwtAuthorizationFilter(authenticationManager(), userDao, jwtProperties),
                        UsernamePasswordAuthenticationFilter.class
                )

                .authorizeRequests()

                /**
                 * 로그인 URI는 모든 사용자에게 허용
                 */
                .antMatchers("/**/api/v1/members/login").permitAll()
                /**
                 * 회원가입 URI는 모든 사용자에게 허용
                 */
                .antMatchers(HttpMethod.POST, "/api/**/members").permitAll()
                .antMatchers("/api/**/members/**").permitAll()
                /**
                 * 나머지 요청은 모두 인증되어야 한다.
                 */
                .anyRequest().authenticated()
        ;
    }
}
