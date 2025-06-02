package com.social.websocket.verify;

import com.nimbusds.jose.JWSVerifier;
import com.nimbusds.jose.crypto.factories.DefaultJWSVerifierFactory;
import com.nimbusds.jose.jwk.JWK;
import com.nimbusds.jose.jwk.JWKMatcher;
import com.nimbusds.jose.jwk.JWKSelector;
import com.nimbusds.jose.jwk.KeyType;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.jwk.source.JWKSourceBuilder;
import com.nimbusds.jose.proc.JWSVerifierFactory;
import com.nimbusds.jose.proc.SecurityContext;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Component
public class JwtVerifier {

    private final JWKSource<SecurityContext> jwkSource;
    private final JWSVerifierFactory jwsVerifierFactory;

    public JwtVerifier() {
        this.jwkSource = createJWKSource("http://localhost:8080/realms/app-chat/protocol/openid-connect/certs");
        this.jwsVerifierFactory = new DefaultJWSVerifierFactory();
    }

    private JWKSource<SecurityContext> createJWKSource(String jwksUri) {
        try {
            // Sử dụng JWKSourceBuilder mới
            return JWKSourceBuilder.create(new URL(jwksUri))
                    .cache(true) // cache duration
                    .rateLimited(false) // không giới hạn rate
                    .build();
        } catch (Exception e) {
            throw new RuntimeException("Failed to create JWK source", e);
        }
    }

    public boolean validateToken(String token) {
        try {
            SignedJWT signedJWT = SignedJWT.parse(token);

            // Lấy JWK selector
            JWKSelector jwkSelector = new JWKSelector(
                    new JWKMatcher.Builder()
                            .keyType(KeyType.RSA)
                            .keyID(signedJWT.getHeader().getKeyID())
                            .build()
            );

            // Lấy JWK từ source
            List<JWK> jwks = jwkSource.get(jwkSelector, null);
            if (jwks.isEmpty()) {
//                log.warn("No matching JWK found for key ID: {}", signedJWT.getHeader().getKeyID());
                return false;
            }

            // Tạo verifier từ JWK
            JWK jwk = jwks.get(0);
            JWSVerifier verifier = jwsVerifierFactory.createJWSVerifier(
                    signedJWT.getHeader(),
                    jwk.toRSAKey().toPublicKey()
            );

            // Verify signature
            if (!signedJWT.verify(verifier)) {
                return false;
            }

            // Validate claims
            return validateClaims(signedJWT.getJWTClaimsSet());

        } catch (Exception e) {
//            log.error("JWT validation failed", e);
            return false;
        }
    }

    private boolean validateClaims(JWTClaimsSet claimsSet) {
        Date now = new Date();

        // Kiểm tra expiration time
        if (claimsSet.getExpirationTime() != null &&
                claimsSet.getExpirationTime().before(now)) {
//            log.debug("Token expired at: {}", claimsSet.getExpirationTime());
            return false;
        }

        // Kiểm tra not before time
        if (claimsSet.getNotBeforeTime() != null &&
                claimsSet.getNotBeforeTime().after(now)) {
//            log.debug("Token not valid before: {}", claimsSet.getNotBeforeTime());
            return false;
        }

        // Kiểm tra issued at (tùy chọn)
        if (claimsSet.getIssueTime() != null &&
                claimsSet.getIssueTime().after(now)) {
//            log.debug("Token issued in the future: {}", claimsSet.getIssueTime());
            return false;
        }

        return true;
    }

    public JWTClaimsSet extractClaims(String token) {
        try {
            SignedJWT signedJWT = SignedJWT.parse(token);
            return signedJWT.getJWTClaimsSet();
        } catch (Exception e) {
            throw new RuntimeException("Cannot extract claims from JWT", e);
        }
    }

    public String extractSubject(String token) {
        return extractClaims(token).getSubject();
    }

    public String extractPreferredUsername(String token) {
        try {
            return extractClaims(token).getStringClaim("preferred_username");
        } catch (Exception e) {
            return extractSubject(token);
        }
    }

    @SuppressWarnings("unchecked")
    public List<String> extractRealmRoles(String token) {
        try {
            JWTClaimsSet claims = extractClaims(token);
            Map<String, Object> realmAccess = claims.getJSONObjectClaim("realm_access");

            if (realmAccess != null && realmAccess.containsKey("roles")) {
                return (List<String>) realmAccess.get("roles");
            }

            return Collections.emptyList();
        } catch (Exception e) {
//            log.error("Error extracting realm roles", e);
            return Collections.emptyList();
        }
    }

    @SuppressWarnings("unchecked")
    public List<String> extractClientRoles(String token, String clientId) {
        try {
            JWTClaimsSet claims = extractClaims(token);
            Map<String, Object> resourceAccess = claims.getJSONObjectClaim("resource_access");

            if (resourceAccess != null && resourceAccess.containsKey(clientId)) {
                Map<String, Object> clientAccess = (Map<String, Object>) resourceAccess.get(clientId);
                if (clientAccess.containsKey("roles")) {
                    return (List<String>) clientAccess.get("roles");
                }
            }

            return Collections.emptyList();
        } catch (Exception e) {
//            log.error("Error extracting client roles for client: {}", clientId, e);
            return Collections.emptyList();
        }
    }
}
