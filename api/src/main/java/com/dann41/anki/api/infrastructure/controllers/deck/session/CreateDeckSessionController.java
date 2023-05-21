package com.dann41.anki.api.infrastructure.controllers.deck.session;

import com.dann41.anki.auth.infrastructure.auth.AuthUser;
import com.dann41.anki.core.deck.application.sessionstarter.StartSessionCommand;
import com.dann41.anki.shared.application.CommandBus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.dann41.anki.auth.infrastructure.auth.AuthUtils.userIdFrom;

@RestController
public class CreateDeckSessionController {

  private final CommandBus commandBus;

  public CreateDeckSessionController(CommandBus commandBus) {
    this.commandBus = commandBus;
  }

  @PutMapping("/api/decks/{deckId}/session")
  public ResponseEntity<Void> startSession(
      @PathVariable("deckId") String deckId,
      Authentication authentication
  ) {
    String userId = userIdFrom(authentication);
    commandBus.publish(new StartSessionCommand(
        deckId,
        userId
    ));

    return ResponseEntity.ok(null);
  }

}
