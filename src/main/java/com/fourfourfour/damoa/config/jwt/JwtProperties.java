package com.fourfourfour.damoa.config.jwt;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Getter
@Component
public class JwtProperties {

    String HEADER = "Authorization";
    String PREFIX = "Bearer ";
    String CLAIM = "userEmail";

}
