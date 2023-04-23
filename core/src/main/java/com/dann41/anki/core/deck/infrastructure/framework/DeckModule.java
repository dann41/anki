package com.dann41.anki.core.deck.infrastructure.framework;

import com.dann41.anki.shared.infrastructure.framework.SharedModule;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@ComponentScan(basePackages = {"com.dann41.anki.core.deck.infrastructure.framework"})
@Import(SharedModule.class)
public class DeckModule {
}
