package com.dann41.anki.api.infrastructure.auth;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@ConfigurationProperties("jwt")
@Configuration
public class JwtConfig {
  private String publicKey;
  private String privateKey;

  public String publicKey() {
    return publicKey;
  }

  public JwtConfig setPublicKey(String publicKey) {
    this.publicKey = publicKey;
    return this;
  }

  public String privateKey() {
    return privateKey;
  }

  public JwtConfig setPrivateKey(String privateKey) {
    this.privateKey = privateKey;
    return this;
  }
}
