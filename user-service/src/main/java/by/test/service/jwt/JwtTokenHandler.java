package by.test.service.jwt;

import by.test.config.properties.JWTProperty;
import by.test.dao.entity.UserEntity;
import io.jsonwebtoken.*;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.concurrent.TimeUnit;


@Service
public class JwtTokenHandler {

    private final JWTProperty property;

    public JwtTokenHandler(JWTProperty property) {
        this.property = property;
    }

    public String generateAccessToken(UserDetails user, String role) {
        return generateAccessToken(user.getUsername(), role);
    }

    public String generateAccessToken(String name, String role) {
        return Jwts.builder()
            .setSubject(name)
            .claim("role", role)
            .setIssuer("UserKrainet")
            .setIssuedAt(new Date())
            .setExpiration(new Date(System.currentTimeMillis() + TimeUnit.DAYS.toMillis(7))) // 1 week
            .signWith(SignatureAlgorithm.HS256, property.getSecret())
            .compact();
    }

    public String getUsername(String token) {
        Claims claims = Jwts.parser()
            .setSigningKey(property.getSecret())
            .parseClaimsJws(token)
            .getBody();

        return claims.getSubject();
    }

    public String getUserrole(String token) {
        Claims claims = Jwts.parser()
            .setSigningKey(property.getSecret())
            .parseClaimsJws(token)
            .getBody();

        return claims.get("role", UserEntity.class).getRole().name();
    }

    public Date getExpirationDate(String token) {
        Claims claims = Jwts.parser()
            .setSigningKey(property.getSecret())
            .parseClaimsJws(token)
            .getBody();

        return claims.getExpiration();
    }

    public boolean validate(String token) {
        try {
            Jwts.parser().setSigningKey(property.getSecret()).parseClaimsJws(token);
            return true;
        } catch (SignatureException |
                 MalformedJwtException |
                 ExpiredJwtException |
                 UnsupportedJwtException |
                 IllegalArgumentException ignored) {
        }
        return false;
    }
}