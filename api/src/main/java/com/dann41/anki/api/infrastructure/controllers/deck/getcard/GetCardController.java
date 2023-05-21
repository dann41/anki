package com.dann41.anki.api.infrastructure.controllers.deck.getcard;

import com.dann41.anki.core.deck.application.cardpicker.CardPickerQuery;
import com.dann41.anki.shared.application.QueryBus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import static com.dann41.anki.auth.infrastructure.auth.AuthUtils.userIdFrom;

@RestController
public class GetCardController {

  private final QueryBus queryBus;

  public GetCardController(QueryBus queryBus) {
    this.queryBus = queryBus;
  }

  @GetMapping("/api/decks/{deckId}/cards/next")
  public ResponseEntity<CardResponse> getNextQuestion(
    @PathVariable("deckId") String deckId,
    Authentication authentication
  ) {
    var cardPickerResponse = queryBus.publish(new CardPickerQuery(
        deckId,
        userIdFrom(authentication)
    ));

    return ResponseEntity.ok(
        new CardResponse(
            cardPickerResponse.id(),
            cardPickerResponse.question(),
            cardPickerResponse.answer()
        )
    );
  }

}
