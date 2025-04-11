package cn.bit.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.NonNull;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public class JwtUtil {

    private final String secret;
    private final Long accessTokenExpiration;
    private final Long refreshTokenExpiration;
    private final Long internalTokenExpiration;

    public JwtUtil(@NonNull String secret, @NonNull Long accessTokenExpiration, @NonNull Long refreshTokenExpiration,
        @NonNull Long internalTokenExpiration) {
        this.secret = secret;
        this.accessTokenExpiration = accessTokenExpiration;
        this.refreshTokenExpiration = refreshTokenExpiration;
        this.internalTokenExpiration = internalTokenExpiration;
    }

    public String generateAccessToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        return createToken(claims, userDetails.getUsername(), accessTokenExpiration);
    }

    public String generateInternalToken(String serviceName) {
        Map<String, Object> claims = new HashMap<>();
        return createToken(claims, serviceName, internalTokenExpiration);
    }

    public String generateRefreshToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        return createToken(claims, userDetails.getUsername(), refreshTokenExpiration);
    }

    private String createToken(Map<String, Object> claims, String subject, Long expiration) {
        return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis()))
            .setExpiration(new Date(System.currentTimeMillis() + expiration * 1000))
            .signWith(SignatureAlgorithm.HS256, secret).compact();
    }

    public Boolean validateToken(String token, UserDetails userDetails) {
        final String data = extractData(token);
        return (data.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    public Boolean validateInternalToken(String token, String serviceName) {
        final String data = extractData(token);
        return (data.equals(serviceName) && !isTokenExpired(token));
    }

    public String extractData(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    private <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
    }

    private Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    public Date getAccessTokenExpiration() {
        return new Date(System.currentTimeMillis() + accessTokenExpiration * 1000);
    }

    public Date getRefreshTokenExpiration() {
        return new Date(System.currentTimeMillis() + refreshTokenExpiration * 1000);
    }
}
