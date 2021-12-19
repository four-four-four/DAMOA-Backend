package com.fourfourfour.damoa.config.jwt;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Getter
@Component
public class JwtProperties {

    @Value("${jwt.key}")
    private String jwtKey;
    @Value("${jwt.token-validity-in-seconds}")
    private long tokenValidityInMilliseconds;
    private final String HEADER = "Authorization";
    private final String PREFIX = "Bearer ";
    private final String CLAIM = "userEmail";

}
