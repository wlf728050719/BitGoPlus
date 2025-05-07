package cn.bit.security.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.Getter;
import lombok.NonNull;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public class JwtUtil {

    private final String secret;
    @Getter
    private final Long accessTokenExpiration; //accessToken超时时间（单位秒）
    @Getter
    private final Long refreshTokenExpiration; //refreshToken超时时间（单位秒）
    @Getter
    private final Long internalTokenExpiration; //internalToken超时时间（单位秒）

    public JwtUtil(@NonNull String secret, @NonNull Long accessTokenExpiration, @NonNull Long refreshTokenExpiration,
        @NonNull Long internalTokenExpiration) {
        this.secret = secret;
        this.accessTokenExpiration = accessTokenExpiration;
        this.refreshTokenExpiration = refreshTokenExpiration;
        this.internalTokenExpiration = internalTokenExpiration;
    }

    /**
     * 生成access token
     * 
     * @param username 用户名
     * @return token
     */
    public String generateAccessToken(String username) {
        Map<String, Object> claims = new HashMap<>();
        return createToken(claims, username, accessTokenExpiration);
    }

    /**
     * 生成internal token
     * 
     * @param serviceName 服务名
     * @return token
     */
    public String generateInternalToken(String serviceName) {
        Map<String, Object> claims = new HashMap<>();
        return createToken(claims, serviceName, internalTokenExpiration);
    }

    /**
     * 生成refresh token
     * 
     * @param username 用户名
     * @return token
     */
    public String generateRefreshToken(String username) {
        Map<String, Object> claims = new HashMap<>();
        return createToken(claims, username, refreshTokenExpiration);
    }

    /**
     * 提取token中加密字段
     * 
     * @param token token
     * @return username/serviceName
     */
    public String extractData(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    /**
     * 提取token中过期时间
     * 
     * @param token token
     * @return 过期时间
     */
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

    private String createToken(Map<String, Object> claims, String subject, Long expiration) {
        return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expiration * 1000))
                .signWith(SignatureAlgorithm.HS256, secret).compact();
    }
}
