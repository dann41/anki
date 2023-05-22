package com.dann41.anki.api.infrastructure.controllers.deck.getstate;

import com.dann41.anki.core.deck.application.statefinder.FindStatusQuery;
import com.dann41.anki.shared.application.QueryBus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import static com.dann41.anki.auth.infrastructure.auth.AuthUtils.userIdFrom;

@RestController
public class GetDeckStateController {

  private final QueryBus queryBus;

  public GetDeckStateController(QueryBus queryBus) {
    this.queryBus = queryBus;
  }

  @GetMapping("/api/decks/{deckId}")
  public ResponseEntity<GetDeckStateResponse> getDeckState(
      @PathVariable("deckId") String deckId,
      Authentication authentication
  ) {
    var response = queryBus.publish(new FindStatusQuery(
        deckId,
        userIdFrom(authentication)
    ));

    return ResponseEntity.ok(
        new GetDeckStateResponse(
            deckId,
            response.totalCards(),
            response.unplayedCards(),
            response.greenCards(),
            response.orangeCards(),
            response.redCards(),
            response.lastSession()
        )
    );
  }

}
