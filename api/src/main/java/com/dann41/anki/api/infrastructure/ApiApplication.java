package com.dann41.anki.api.infrastructure;

import com.dann41.anki.api.infrastructure.configuration.ApiConfiguration;
import com.dann41.anki.auth.infrastructure.framework.configuration.AuthModule;
import com.dann41.anki.core.shared.infrastructure.framework.CoreApiModule;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(
    scanBasePackageClasses = {CoreApiModule.class, ApiConfiguration.class, AuthModule.class}
)
public class ApiApplication {


  public static void main(String[] args) {
    SpringApplication.run(ApiApplication.class, args);
  }

}
