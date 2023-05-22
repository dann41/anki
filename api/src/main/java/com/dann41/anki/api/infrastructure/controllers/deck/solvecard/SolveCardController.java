package com.dann41.anki.api.infrastructure.controllers.deck.solvecard;

import com.dann41.anki.core.deck.application.cardsolver.SolveCardCommand;
import com.dann41.anki.shared.application.CommandBus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import static com.dann41.anki.auth.infrastructure.auth.AuthUtils.userIdFrom;

@RestController
public class SolveCardController {

  private final CommandBus commandBus;

  public SolveCardController(CommandBus commandBus) {
    this.commandBus = commandBus;
  }

  @PostMapping("/api/decks/{deckId}/evaluations")
  public ResponseEntity<Void> solveCard(
      @PathVariable("deckId") String deckId,
      @RequestBody CardSolutionRequest cardSolution,
      Authentication authentication
  ) {
    var userId = userIdFrom(authentication);
    commandBus.publish(new SolveCardCommand(
        deckId,
        userId,
        cardSolution.cardId(),
        cardSolution.box())
    );

    return ResponseEntity.status(201).build();
  }

}
