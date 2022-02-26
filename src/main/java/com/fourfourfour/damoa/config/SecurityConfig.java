package com.fourfourfour.damoa.config;

import com.fourfourfour.damoa.api.member.repository.MemberRepository;
import com.fourfourfour.damoa.common.auth.JwtAuthenticationFilter;
import com.fourfourfour.damoa.common.auth.PrincipalDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.filter.CorsFilter;

@Configuration
/**
 * spring security filter(SecurityConfig 클래스)가 스프링 필터체인에 등록이 된다.
 */
@EnableWebSecurity
/**
 * secured 애노테이션 활성화, preAuthorize 애노테이션 활성화
 */
@EnableGlobalMethodSecurity(prePostEnabled = true)
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final CorsFilter corsFilter;

    private final MemberRepository memberRepository;

    private final PrincipalDetailsService principalDetailsService;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        daoAuthenticationProvider.setUserDetailsService(principalDetailsService);
        return daoAuthenticationProvider;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) {
        auth.authenticationProvider(authenticationProvider());
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
                 * 토큰 방식을 사용하므로
                 * 기존의 HTTP 로그인 방식(ID, PW를 보내는 방식) 비활성화
                 */
                .httpBasic().disable()

                .addFilter(corsFilter)
                .addFilter(new JwtAuthenticationFilter(authenticationManager(), memberRepository))

                .authorizeRequests()
                .anyRequest().permitAll();
        ;
    }
}
