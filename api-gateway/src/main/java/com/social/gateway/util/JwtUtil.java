//package com.microservices.apigateway.util;
//
//import io.jsonwebtoken.Claims;
//import io.jsonwebtoken.Jwts;
//import io.jsonwebtoken.io.Decoders;
//import io.jsonwebtoken.security.Keys;
//import jakarta.annotation.PostConstruct;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.stereotype.Component;
//
//import java.security.Key;
//import java.util.Date;
//
//@Component
//public class JwtUtil {
//
//    @Value("${jwt.secret}")
//    private String secret;
//
//    private Key key;
//
//    private Key getSignKey() {
//        byte[] keyBytes = Decoders.BASE64.decode(secret);
//        return Keys.hmacShaKeyFor(keyBytes);
//    }
//
//    public Claims getAllClaimsFromToken(String token) {
//        return Jwts.parserBuilder().setSigningKey(getSignKey()).build().parseClaimsJws(token).getBody();
//    }
//
//    private boolean isTokenExpired(String token) {
//        return this.getAllClaimsFromToken(token).getExpiration().before(new Date());
//    }
//
//    public boolean isInvalid(String token) {
//        return this.isTokenExpired(token);
//    }
//
//}
