//package com.sanplink.api.user;
//
//import com.nimbusds.jose.*;
//import com.nimbusds.jose.crypto.MACSigner;
//import com.nimbusds.jose.crypto.MACVerifier;
//import com.nimbusds.jwt.JWTClaimsSet;
//import com.nimbusds.jwt.SignedJWT;
//import org.springframework.stereotype.Service;
//
//
//import javax.xml.crypto.Data;
//import java.time.Instant;
//import java.util.Date;
//
//@Service
//public class TokenProvider {
//    private static final String SECURITY_KEY = "this-is-a-32-byte-long-secret-key!";
//
//    // JWT 생성 메서드
//    public String createJwt(String username, int duration) {
//        try{
//            Instant now = Instant.now();
//            Instant exprTime = now.plusSeconds(duration);
//
//
//            JWTClaimsSet claimsSet = new JWTClaimsSet.Builder()
//                    .subject(username)
//                    .issueTime(Date.from(now))
//                    .expirationTime(Date.from(exprTime))
//                    .build();
//
//            SignedJWT signedJWT = new SignedJWT(
//                    new JWSHeader(JWSAlgorithm.HS256),
//                    claimsSet
//            );
//
//            JWSSigner signer = new MACSigner(SECURITY_KEY.getBytes());
//            signedJWT.sign(signer);
//
//            return signedJWT.serialize();
//        } catch (JOSEException e) {
//            return null;
//        }
//    }
//
//
//    public String validateJwt(String token) {
//        try {
//            // 서명 확인을 통한 JWT 검증
//            SignedJWT signedJWT = SignedJWT.parse(token);
//            JWSVerifier verifier = new MACVerifier(SECURITY_KEY.getBytes());
//            if (signedJWT.verify(verifier)) {
//                return signedJWT.getJWTClaimsSet().getSubject();
//            } else {
//                // 서명이 유효하지 않은 경우
//                return null;
//            }
//        } catch (Exception e) {
//            return null;
//        }
//    }
//
//}
