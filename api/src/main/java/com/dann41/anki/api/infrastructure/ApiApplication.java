package com.dann41.anki.api.infrastructure;

import com.dann41.anki.api.infrastructure.configuration.ApiConfiguration;
import com.dann41.anki.core.shared.infrastructure.framework.CoreModule;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(
    scanBasePackageClasses = {CoreModule.class, ApiConfiguration.class}
)
public class ApiApplication {


  public static void main(String[] args) {
    SpringApplication.run(ApiApplication.class, args);
  }

}
