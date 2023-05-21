package com.dann41.anki.api.infrastructure.controllers;

import com.dann41.anki.auth.infrastructure.auth.AuthUser;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import static com.dann41.anki.auth.infrastructure.auth.AuthUtils.userIdFrom;

@RestController
public class AuthenticatedController {
  @GetMapping("/auth/hello")
  public Mono<String> sayHello(Authentication authentication) {
    String userId = userIdFrom(authentication);
    String userName = ((AuthUser) authentication.getPrincipal()).getUsername();

    return Mono.fromCallable(() -> "Hi there " + userName + ". ID: " + userId);
  }
}
