package com.dann41.anki.core.user.infrastructure.framework;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackageClasses = UserModule.class)
public class UserModule {
}
