package com.libv.util;

import cn.hutool.core.util.StrUtil;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 * JWT使用
 */
public class JwtUtil {
    // token过期时间
    public static final long EXPIRE = 60 * 60 * 24 * 1000 * 30L;
    // token加密密钥
    public static final String APP_SECRET = "irrigation_qaq123456789";

    public static String getJwtToken(Long id) {
        Date date = new Date(System.currentTimeMillis() + EXPIRE);
        return Jwts.builder()
                .setHeaderParam("typ", "JWT")
                .setHeaderParam("alg", "HS256")
                .setSubject("irrigation_user")
                .setIssuedAt(new Date())
                // 设置过期时间
                .setExpiration(date)
                // 设置token主体名称id
                .claim("id", id)
                // 签名哈希
                .signWith(SignatureAlgorithm.HS256, APP_SECRET)
                .compact();
    }

    /**
     * 判断token是否有效
     */
    public static boolean checkToken(String token) {
        if (StrUtil.isEmpty(token)) return false;
        try {
            Jwts.parser().setSigningKey(APP_SECRET).parseClaimsJws(token);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    /**
     * 获取请求中的id
     */
    public static Long getIdFromRequest(HttpServletRequest request) {
        String token = request.getHeader("token");
        if (StrUtil.isEmpty(token)) return null;
        long id;
        try {
            id = ((Number) Jwts.parser().setSigningKey(APP_SECRET).parseClaimsJws(token).getBody().get("id")).longValue();
        } catch (Exception e) {
            return null;
        }
        return id;
    }
}