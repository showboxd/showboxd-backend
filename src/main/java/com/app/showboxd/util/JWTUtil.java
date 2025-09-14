package com.app.showboxd.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Strings;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.IOException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;


public class JWTUtil {

    public static final String AUTH_HEADER_PREFIX = "Bearer ";

    public static String generateJwtToken(UserDetails userDetails, int expirationDays, String jwtSecretKey) {
        Date expirationDate = Date.from(Instant.now().plus(expirationDays, ChronoUnit.SECONDS));
        return Jwts.builder().
                        subject(userDetails.getUsername())
                        .claim("user_id", userDetails.getUsername())
                        .issuedAt(new Date())
                        .expiration(expirationDate)
                        .signWith(Keys.hmacShaKeyFor(jwtSecretKey.getBytes())).compact();
    }

    public static String extractHeaderToken(String header) throws IOException {
        if (StringUtils.isEmpty(header)) {
            throw new IOException("Authorization header cannot be blank!");
        }

        if (header.length() < AUTH_HEADER_PREFIX.length()) {
            throw new IOException("Invalid Authorization header size.");
        }

        return header.substring(AUTH_HEADER_PREFIX.length());
    }

    public static Claims parseJwtToken(String jwtHeaderToken, String jwtSecretKey) {
        return Jwts.parser()
                .verifyWith(Keys.hmacShaKeyFor(jwtSecretKey.getBytes()))
                .build()
                .parseSignedClaims(jwtHeaderToken)
                .getPayload();

    }

    private static boolean isTokenExpired(Claims claims) {
        return claims.getExpiration().before(new Date());
    }

    public static boolean isTokenValid(UserDetails userDetails, Claims claims) {
        String userName = getUserNameFromClaims(claims);
        return Strings.CS.equals(userDetails.getUsername(), userName) && !isTokenExpired(claims);
    }

    public static String getUserNameFromClaims(Claims claims) {
        return claims.getSubject();
    }
}
