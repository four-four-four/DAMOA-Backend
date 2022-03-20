package com.fourfourfour.damoa.common.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

/**
 * jwt 토큰 유틸 정의.
 */
@Slf4j
@Component
public class JwtTokenUtil {

    private static String secretKey;

    private static Integer expirationTime;

    public static final String TOKEN_PREFIX = "Bearer ";

    public static final String HEADER_STRING = "Authorization";

    public static final String ISSUER = "damoa.com";

    @Autowired
    public JwtTokenUtil(@Value("${jwt.secret}") String secretKey, @Value("${jwt.expiration}") Integer expirationTime) {
        this.secretKey = secretKey;
        this.expirationTime = expirationTime;
    }

    public static String getToken(String email) {
        Date expires = JwtTokenUtil.getTokenExpiration(expirationTime);
        return JWT.create()
                .withSubject(email)
                .withExpiresAt(expires)
                .withIssuer(ISSUER)
                .withIssuedAt(Date.from(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant()))
                .sign(Algorithm.HMAC512(secretKey.getBytes()));
    }

    public static Date getTokenExpiration(int expirationTime) {
        Date now = new Date();
        return new Date(now.getTime() + expirationTime);
    }

    public static JWTVerifier getVerifier() {
        return JWT
                .require(Algorithm.HMAC512(secretKey.getBytes()))
                .withIssuer(ISSUER)
                .build();
    }

    public static void handleError(String token) {
        JWTVerifier verifier = JWT
                .require(Algorithm.HMAC512(secretKey.getBytes()))
                .withIssuer(ISSUER)
                .build();

        try {
            verifier.verify(token.replace(TOKEN_PREFIX, ""));
        } catch (AlgorithmMismatchException ex) {
            log.error(ex.getClass().toString());
            ex.printStackTrace();

            throw ex;
        } catch (InvalidClaimException ex) {
            log.error(ex.getClass().toString());
            ex.printStackTrace();

            throw ex;
        } catch (SignatureGenerationException ex) {
            log.error(ex.getClass().toString());
            ex.printStackTrace();

            throw ex;
        } catch (SignatureVerificationException ex) {
            log.error(ex.getClass().toString());
            ex.printStackTrace();

            throw ex;
        } catch (TokenExpiredException ex) {
            log.error(ex.getClass().toString());
            ex.printStackTrace();

            throw ex;
        } catch (JWTCreationException ex) {
            log.error(ex.getClass().toString());
            ex.printStackTrace();

            throw ex;
        } catch (JWTDecodeException ex) {
            log.error(ex.getClass().toString());
            ex.printStackTrace();

            throw ex;
        } catch (JWTVerificationException ex) {
            log.error(ex.getClass().toString());
            ex.printStackTrace();

            throw ex;
        } catch (Exception ex) {
            log.error(ex.getClass().toString());
            ex.printStackTrace();

            throw ex;
        }
    }
}
