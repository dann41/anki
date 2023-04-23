package com.dann41.anki.auth.infrastructure.auth;

import org.springframework.http.HttpHeaders;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.server.authentication.ServerAuthenticationConverter;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

public class BearerAuthenticationConverter implements ServerAuthenticationConverter {

  public static final String BEARER = "Bearer ";

  private final TokenService tokenService;

  public BearerAuthenticationConverter(TokenService tokenService) {
    this.tokenService = tokenService;
  }

  @Override
  public Mono<Authentication> convert(ServerWebExchange exchange) {
    var request = exchange.getRequest();
    var authorization = request.getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
    if (!StringUtils.startsWithIgnoreCase(authorization, "bearer ")) {
      return Mono.empty();
    }

    var token = (authorization.length() <= BEARER.length()) ? "" : authorization.substring(BEARER.length());
    if (token.isBlank()) {
      return Mono.empty();
    }

    return Mono.just(token)
        .map(BearerToken::new);
  }
}
