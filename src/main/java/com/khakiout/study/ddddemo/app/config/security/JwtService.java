package com.khakiout.study.ddddemo.app.config.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
public class JwtService {

    private static final String KEY_ENABLED = "enabled";
    private static final String KEY_ACCESS = "access";

    @Value("${auth.password.encoder.secret}")
    private String secret;

    @Value("${auth.session.duration.minutes}")
    private int expirationTimeInMinutes;

    @Autowired
    private DateService dateService;

    public Claims getAllClaimsFromToken(String token) {
        return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
    }

    public String getUsernameFromToken(String token) {
        return getAllClaimsFromToken(token).getSubject();
    }

    public Date getExpirationDateFromToken(String token) {
        return getAllClaimsFromToken(token).getExpiration();
    }

    private Boolean isTokenExpired(String token) {
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(dateService.newDate());
    }

    /**
     * Generate a token based on the user details.
     *
     * @param user the user details.
     * @return the generated token.
     */
    public String generateToken(UserDetails user) {
        Map<String, Object> claims = new HashMap<>();
        claims.put(KEY_ACCESS, user.getAuthorities());
        claims.put(KEY_ENABLED, user.isEnabled());

        return doGenerateToken(claims, user.getUsername());
    }

    private String doGenerateToken(Map<String, Object> claims, String username) {

        long expirationTimeSeconds = expirationTimeInMinutes * 60L;

        final Date createdDate = dateService.newDate();
        final Date expirationDate = new Date(createdDate.getTime() + expirationTimeSeconds * 1000);
        return Jwts.builder().setClaims(claims).setSubject(username).setIssuedAt(createdDate)
            .setExpiration(expirationDate).signWith(SignatureAlgorithm.HS512, secret)
            .compact();
    }

    public Boolean validateToken(String token) {
        return !isTokenExpired(token);
    }
}
