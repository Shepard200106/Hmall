package com.gulimall.common.utils;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;

import java.security.Key;
import java.util.Date;

/**
 * JWT 工具类（基于 jjwt 0.11.5）
 */
public class JwtUtils {

    // 建议把这个秘钥长度 ≥ 32 字节（256 位），否则 hmacShaKeyFor 会报错
    private static final String SECRET_KEY = "gulimall-secret-key-1234567890-abcd-efgh"; // ≥32字符
    private static final Key KEY = Keys.hmacShaKeyFor(SECRET_KEY.getBytes());

    // 有效期：2小时
    private static final long EXPIRATION_TIME = 1000 * 60 * 60 * 2;

    /**
     * 生成 JWT Token
     */
    public static String generateToken(Long userId, String username) {
        return Jwts.builder()
                .claim("userId", userId)
                .claim("username", username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(KEY, SignatureAlgorithm.HS256) // ✅ 使用 Key
                .compact();
    }

    /**
     * 解析 Token 获取 userId
     */
    public static Long getUserId(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(KEY) // ✅ 使用 Key
                .build()
                .parseClaimsJws(token)
                .getBody();

        return claims.get("userId", Long.class);
    }
}
