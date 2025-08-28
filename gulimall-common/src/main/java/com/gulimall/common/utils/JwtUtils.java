package com.gulimall.common.utils;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;

/**
 * JWT 工具类（基于 jjwt 0.11.5）
 */
public class JwtUtils {

    // 秘钥长度 ≥ 32 字节（256 位）
    private static final String SECRET_KEY = "gulimall-secret-key-1234567890-abcd-efgh";
    private static final Key KEY;

    // 有效期：2小时
    private static final long EXPIRATION_TIME = 1000 * 60 * 60 * 2;

    // 静态初始化块（显式处理异常，便于排查问题）
    static {
        Key tempKey = null;
        try {
            // 明确指定UTF-8编码，避免平台默认编码导致密钥长度变化
            byte[] keyBytes = SECRET_KEY.getBytes(StandardCharsets.UTF_8);
            // 验证密钥长度（JJWT内部也会验证，但显式验证便于调试）
            if (keyBytes.length < 32) {
                throw new IllegalArgumentException("JWT密钥长度必须至少32个字符（256位），当前长度：" + keyBytes.length);
            }
            tempKey = Keys.hmacShaKeyFor(keyBytes);
            System.out.println("JwtUtils初始化成功，密钥长度：" + keyBytes.length + "字节");
        } catch (Exception e) {
            // 捕获所有初始化异常，避免类加载失败
            System.err.println("JwtUtils初始化失败：" + e.getMessage());
            e.printStackTrace();
            // 初始化失败时抛出RuntimeException，阻止应用启动（避免后续更隐蔽的错误）
            throw new RuntimeException("JWT工具类初始化失败，请检查密钥和依赖", e);
        }
        KEY = tempKey;
    }

    /**
     * 生成 JWT Token
     */
    public static String generateToken(Long userId, String username) {
        try {
            return Jwts.builder()
                    .claim("userId", userId)
                    .claim("username", username)
                    .setIssuedAt(new Date())
                    .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                    .signWith(KEY, SignatureAlgorithm.HS256)
                    .compact();
        } catch (Exception e) {
            throw new RuntimeException("生成JWT令牌失败", e);
        }
    }

    /**
     * 解析 Token 获取 userId
     */
    public static Long getUserId(String token) {
        try {
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(KEY)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();

            // 验证令牌是否过期
            if (claims.getExpiration().before(new Date())) {
                throw new ExpiredJwtException(null, claims, "JWT令牌已过期");
            }

            return claims.get("userId", Long.class);
        } catch (ExpiredJwtException e) {
            throw new RuntimeException("JWT令牌已过期", e);
        } catch (SignatureException e) {
            throw new RuntimeException("JWT签名验证失败", e);
        } catch (JwtException e) {
            throw new RuntimeException("JWT令牌解析失败", e);
        }
    }
}
