package org.example.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.SecretKey;

import java.util.Date;

public class JwtGeneration {
    private static final Logger LOG = LoggerFactory.getLogger(JwtGeneration.class);
    private static final SecretKey SECRET_KEY = Keys.hmacShaKeyFor("The Key is Secret so dont share the key".getBytes());
    private static final long EXPIRATION_TIME = (long) 30 * 60 * 1000;
    private JwtGeneration() {
    }

    public static String generateToken(String mail) {
        LOG.info("started token generation");
        return Jwts.builder()
                .setSubject(mail)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(SECRET_KEY, SignatureAlgorithm.HS256)
                .compact();
    }

    public static String extractMail(String token) {
        return getClaims(token).getSubject();
    }

    public static boolean isTokenExpired(String token) {
        return getClaims(token).getExpiration().before(new Date());
    }

    private static Claims getClaims(String token) {
        return Jwts.parser()
                .verifyWith(SECRET_KEY)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public static boolean validateToken(String token, String username) {
        return extractMail(token).equals(username) && !isTokenExpired(token);
    }


}
