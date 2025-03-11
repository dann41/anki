package com.dann41.anki.auth.infrastructure.auth;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;

import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.time.Clock;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Base64;
import java.util.Date;
import java.util.Map;

public class TokenService {

  public static final int ACCESS_TOKEN_DURATION = 15;

  private final Clock clock;
  private final PublicKey publicKey;
  private final PrivateKey privateKey;

  public TokenService(Clock clock, String publicKeyBase64, String privateKeyBase64) {
    this.clock = clock;
    this.publicKey = publicKey(publicKeyBase64);
    this.privateKey = privateKey(privateKeyBase64);
  }

  public String generate(String userId, String username) {
    Date expirationDate = Date.from(
        LocalDateTime.now(clock).plusMinutes(ACCESS_TOKEN_DURATION)
            .toInstant(ZoneOffset.UTC)
    );

    return Jwts.builder()
        .issuer("anki")
        .subject(userId)
        .claims(Map.of(
            "userId", userId,
            "username", username
        ))
        .expiration(expirationDate)
        .signWith(privateKey)
        .compact();
  }

  public JwtAuthenticationToken verify(String jwt) {
    Claims claims;
    try {
      claims = Jwts.parser()
              .verifyWith(publicKey)
              .build()
              .parseSignedClaims(jwt)
              .getPayload();
    } catch (JwtException jwtException) {
      throw new InvalidJwtException(jwt, jwtException);
    }

    if (clock.instant().isAfter(claims.getExpiration().toInstant())) {
      throw new TokenExpiredException();
    }

    return new JwtAuthenticationToken(claims);
  }

  private PublicKey publicKey(String publicKeyBase64) {
    try {
      var key = publicKeyBase64
          .replaceAll("-----BEGIN PUBLIC KEY-----", "")
          .replaceAll("-----END PUBLIC KEY-----", "")
          .replace("\n", "");

      Base64.Decoder decoder = Base64.getDecoder();
      byte[] publicKey = decoder.decode(key);

      X509EncodedKeySpec ks = new X509EncodedKeySpec(publicKey);
      KeyFactory kf = KeyFactory.getInstance("RSA");
      return kf.generatePublic(ks);
    } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
      throw new RuntimeException("Cannot load public key. Cause: " + e.getMessage(), e);
    }
  }

  private PrivateKey privateKey(String privateKeyBase64) {
    try {
      var key = privateKeyBase64
          .replaceAll("-----BEGIN PRIVATE KEY-----", "")
          .replaceAll("-----END PRIVATE KEY-----", "")
          .replace("\n", "");
      Base64.Decoder decoder = Base64.getDecoder();
      byte[] privateKey = decoder.decode(key);

      PKCS8EncodedKeySpec ks = new PKCS8EncodedKeySpec(privateKey);
      KeyFactory kf = KeyFactory.getInstance("RSA");
      return kf.generatePrivate(ks);
    } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
      throw new RuntimeException("Cannot load private key. Cause: " + e.getMessage(), e);
    }
  }

}
