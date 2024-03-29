package com.dann41.anki.api.infrastructure.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
public class HelloController {
  @GetMapping("/hello")
  public Mono<String> sayHello() {
    return Mono.fromCallable(() -> "Hi there!");
  }
}
