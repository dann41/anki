package com.dann41.anki.api.infrastructure.configuration;

import com.dann41.anki.api.infrastructure.auth.JwtConfig;
import com.dann41.anki.api.infrastructure.auth.TokenService;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Clock;

@EnableConfigurationProperties
@Configuration
public class TokenConfiguration {
  @Bean
  TokenService tokenService(Clock clock, JwtConfig jwtConfig) {
    return new TokenService(clock, jwtConfig.publicKey(), jwtConfig.privateKey());
  }
}
