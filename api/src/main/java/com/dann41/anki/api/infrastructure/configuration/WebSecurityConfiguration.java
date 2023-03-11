package com.dann41.anki.api.infrastructure.configuration;

import com.dann41.anki.api.infrastructure.auth.AuthUser;
import com.dann41.anki.core.user.domain.User;
import com.dann41.anki.core.user.domain.UserRepository;
import com.dann41.anki.core.user.domain.Username;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.web.server.SecurityWebFilterChain;
import reactor.core.publisher.Mono;

@Configuration
@EnableWebFluxSecurity
public class WebSecurityConfiguration {

  @Bean
  public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {
    http
        .authorizeExchange((exchangeSpec) -> exchangeSpec
            .pathMatchers("/hello", "/login").permitAll()
            .anyExchange().authenticated()
        )
        .httpBasic(Customizer.withDefaults())
        .csrf().disable();

    return http.build();
  }

  @Bean
  public ReactiveUserDetailsService reactiveUserDetailsService(UserRepository userRepository) {
    return username -> Mono.fromCallable(() -> {
      User user = userRepository.findByUsername(new Username(username));
      return new AuthUser(user);
    });
  }
}
