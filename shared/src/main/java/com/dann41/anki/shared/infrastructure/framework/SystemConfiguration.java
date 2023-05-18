package com.dann41.anki.shared.infrastructure.framework;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Clock;

@Configuration
public class SystemConfiguration {

  public SystemConfiguration() {
    System.out.println("System config");
  }
  @Bean
  Clock clock() {
    return Clock.systemUTC();
  }

  @Bean
  ObjectMapper objectMapper() {
    return new ObjectMapper()
        .registerModule(new JavaTimeModule());
  }

}
