package com.dann41.anki.core.shared.infrastructure.framework;

import com.dann41.anki.core.cardcollection.infrastructure.framework.CardCollectionModule;
import com.dann41.anki.core.deck.infrastructure.framework.DeckModule;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import({DeckModule.class, CardCollectionModule.class})
public class CoreModule {
}
