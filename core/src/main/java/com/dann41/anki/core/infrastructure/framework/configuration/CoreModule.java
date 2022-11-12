package com.dann41.anki.core.infrastructure.framework.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import(DeckConfiguration.class)
public class CoreModule {
}
