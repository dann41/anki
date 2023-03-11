package com.dann41.anki.api.infrastructure.controllers;

import com.dann41.anki.api.infrastructure.auth.AuthUser;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
public class AuthenticatedController {
  @GetMapping("/auth/hello")
  public Mono<String> sayHello(Authentication authentication) {
    String userId = ((AuthUser) authentication.getPrincipal()).getId();
    String userName = ((AuthUser) authentication.getPrincipal()).getUsername();

    return Mono.fromCallable(() -> "Hi there " + userName + ". ID: " + userId);
  }
}
