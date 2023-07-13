package com.nju.boot.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.*;

public class JwtTokenUtils {

    //在http header中的名字
    public final static String TOKEN_HEADER = "Authorization";

    //一个小时过期
    public final static long EXPIRATION_TIME = 1000 * 60 * 60 * 60;

    // 应用密钥
    private static final String SIGNATURE = "nju.joaut";

    /**
     * 生成Token
     */
    public static String createToken(String username, long expiration) {
        return Jwts.builder()
                //header
                .setHeaderParam("typ", "JWT")
                .setHeaderParam("alg", "HS256")
                //payload
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                //signature
                .signWith(SignatureAlgorithm.HS256, SIGNATURE)
                .compact();
    }

    /**
     * 获取token body
     */
    private static Claims getTokenClaims(String token) {
        Claims claims;
        try {
            claims = Jwts.parser()
                    .setSigningKey(SIGNATURE)
                    .parseClaimsJws(token)
                    .getBody();
        } catch ( ExpiredJwtException e ) {
            claims = e.getClaims();
        }
        return claims;
    }

    /**
     * 从Token中获取username
     */
    public static String getUsername(String token) {
        return getTokenClaims(token).getSubject();
    }

    public static Date getExpiration(String token){
        return getTokenClaims(token).getExpiration();
    }

    /**
     * 校验Token是否过期
     */
    public static boolean isExpiration(String token) {
        return getTokenClaims(token).getExpiration().before(new Date());
    }
}
