package com.dann41.anki.api.infrastructure.controllers.deck.create;

import com.dann41.anki.core.deck.application.deckcreator.CreateDeckCommand;
import com.dann41.anki.shared.application.CommandBus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import static com.dann41.anki.auth.infrastructure.auth.AuthUtils.userIdFrom;

@RestController
public class CreateDeckController {

  private final CommandBus commandBus;

  public CreateDeckController(CommandBus commandBus) {
    this.commandBus = commandBus;
  }

  @PutMapping("/api/decks/{deckId}")
  public ResponseEntity<CreateDeckResponse> execute(
      @PathVariable("deckId") String deckId,
      @RequestBody CreateDeckRequest request,
      Authentication authentication
  ) {
    String userId = userIdFrom(authentication);

    commandBus.publish(new CreateDeckCommand(
        deckId,
        userId,
        request.collectionId()
    ));

    return ResponseEntity.status(201).body(new CreateDeckResponse(deckId));
  }

}
