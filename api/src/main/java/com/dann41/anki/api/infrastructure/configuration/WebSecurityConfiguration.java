package com.dann41.anki.api.infrastructure.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.userdetails.MapReactiveUserDetailsService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
@EnableWebFluxSecurity
public class WebSecurityConfiguration {

  private final PasswordEncoder passwordEncoder;

  public WebSecurityConfiguration(PasswordEncoder passwordEncoder) {
    this.passwordEncoder = passwordEncoder;
  }

  @Bean
  public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {
    http
        .authorizeExchange((exchangeSpec) -> exchangeSpec
            .pathMatchers("/hello").permitAll()
            .anyExchange().authenticated()
        )
        .httpBasic(Customizer.withDefaults());

    return http.build();
  }

  @Bean
  public MapReactiveUserDetailsService userDetailsService() {
    UserDetails user = User
        .builder()
        .username("dani")
        .password("dani")
        .passwordEncoder(passwordEncoder::encode)
        .roles("USER")
        .build();

    return new MapReactiveUserDetailsService(user);
  }
}
