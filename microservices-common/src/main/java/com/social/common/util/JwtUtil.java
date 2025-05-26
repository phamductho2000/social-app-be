package com.social.common.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;

import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.util.Date;
import java.util.Map;

@SuppressWarnings("unchecked")
public class JwtUtil {
    private static final String PUBLIC_KEY = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEA1pY9hf56WVOl4z4FfwCqy3zHXCDXQQY7a5WcNwxJBUAIped8kw5Fw61zlBRKM/5NTFUlFrQMbbirgkiHKQfI5LKs5oiNrsDMT1apOxRJVVby/lLMvq1AVeiw5M1w3rzuqH9UnMg9pX0FCrdAdK4KBJa4ABv/Xpr5aSCsGZwHRL/FZeTKsDCI2j4i+pnPNcIooCSNY6rqulWW0AGZWnc9KJW9MDGOM+lAitlGJKlP2/B+gO7+fZV8Ieil/WJrXM+OTFITSAIA13YS+d7YVxwRoDPrUl4vx/v8+0l2XA0tSKFKHHaA1TdHZVylCIXJJfu1uqb3VfI3nYUbhij2JsdEKQIDAQAB";

    private static final String authServerUrl = "localhost:8080";

    private static final String realm = "app-chat";

    public static Claims validateToken(String token) {
        try {
            PublicKey publicKey = getPublicKey(PUBLIC_KEY);

            // Xác thực chữ ký và parse JWT
            Jws<Claims> jwsClaims = Jwts.parserBuilder()
                    .setSigningKey(publicKey)
                    .build()
                    .parseClaimsJws(token);

            Claims claims = jwsClaims.getBody();

            // Kiểm tra thời hạn
            if (claims.getExpiration().before(new Date())) {
                throw new JwtException("Token đã hết hạn");
            }

            // Kiểm tra issuer
            String expectedIssuer = authServerUrl + "/realms/" + realm;
            if (!expectedIssuer.equals(claims.getIssuer())) {
                throw new JwtException("Issuer không hợp lệ");
            }

            // Trả về thông tin từ JWT
            return claims;

        } catch (JwtException e) {
            throw new RuntimeException("Lỗi xác thực JWT: " + e.getMessage(), e);
        } catch (Exception e) {
            throw new RuntimeException("Token không hợp lệ", e);
        }
    }

    public static PublicKey getPublicKey(String key) throws Exception {
        byte[] byteKey = Base64.getDecoder().decode(key);
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(byteKey);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        return keyFactory.generatePublic(keySpec);
    }

    public static Map<String, Object> getPayloadToken(String token) throws JsonProcessingException {
        String[] parts = token.split("\\.");
        String payloadJson = new String(Base64.getUrlDecoder().decode(parts[1]));
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(payloadJson, Map.class);
    }
}
