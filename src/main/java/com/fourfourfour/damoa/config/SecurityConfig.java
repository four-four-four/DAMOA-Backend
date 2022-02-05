package com.fourfourfour.damoa.config;

import com.fourfourfour.damoa.api.member.repository.MemberRepository;
import com.fourfourfour.damoa.config.jwt.JwtAuthorizationFilter;
import com.fourfourfour.damoa.config.jwt.JwtProperties;
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
    private final MemberRepository memberRepository;
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

//                .formLogin().disable()

                /**
                 * 토큰 방식을 사용하므로
                 * 기존의 HTTP 로그인 방식(ID, PW를 보내는 방식) 비활성화
                 */
                .httpBasic().disable()

                /**
                 * 로그인 필터 등록
                 */
                .addFilterBefore(
                        new JwtAuthorizationFilter(authenticationManager(), memberRepository, jwtProperties),
                        UsernamePasswordAuthenticationFilter.class
                )

                .authorizeRequests()
                .antMatchers(HttpMethod.POST, "/api/v1/members/login").permitAll()
                .antMatchers(HttpMethod.POST, "/api/v1/members/email-register").permitAll()
                .antMatchers(HttpMethod.GET, "/api/v1/members/email/*/exists").permitAll()
                .antMatchers(HttpMethod.GET, "/api/v1/members/nickname/*/exists").permitAll()
                .anyRequest().authenticated()
        ;
    }
}
