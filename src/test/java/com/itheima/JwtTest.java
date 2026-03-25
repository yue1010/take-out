package com.itheima;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.junit.jupiter.api.Test;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class JwtTest {
    @Test
    public void testGen(){
        Map<String, Object> claims =new HashMap<>();
        claims.put("id",1);
        claims.put("usertel","13755556666");
        //生成jwt的代码
        String token = JWT.create()
                .withClaim("user",claims)//添加载荷
                .withExpiresAt(new Date(System.currentTimeMillis()+1000*60*60*12))//添加过期时间
                .sign(Algorithm.HMAC256("takeout"));
        System.out.println(token);
    }
    @Test
    public void testParse(){
        //定义字符串
        String token ="eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9" +
                ".eyJ1c2VyIjp7ImlkIjoxLCJ1c2VydGVsIjoiMTM3NTU1NTY2NjYifSwiZXhwIjoxNzczOTMxMzAxfQ" +
                ".kKVaOgf31dFX8bfHvOxzEmt0qpq9GY0Gwtzp3pZmJoo";
        JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256("takeout")).build();
        DecodedJWT decodedJWT = jwtVerifier.verify(token);//验证token
        Map<String, Claim> claims = decodedJWT.getClaims();
        System.out.println(claims.get("user"));

    }
}
