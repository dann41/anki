package com.dann41.anki.api.infrastructure.controllers.mydecks;

import com.dann41.anki.auth.infrastructure.auth.AuthUser;
import com.dann41.anki.core.deck.application.alldecksfinder.MyDecksFinderQuery;
import com.dann41.anki.shared.application.QueryBus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.stream.Collectors;

import static com.dann41.anki.auth.infrastructure.auth.AuthUtils.userIdFrom;

@RestController
public class GetMyDecksController {

  private final QueryBus queryBus;

  public GetMyDecksController(QueryBus queryBus) {
    this.queryBus = queryBus;
  }

  @GetMapping("/api/mydecks")
  public Mono<List<DeckDto>> getMyDecks(Authentication authentication) {
    return Mono.fromCallable(() -> {
      String userId = userIdFrom(authentication);
      var queryResponse = queryBus.publish(new MyDecksFinderQuery(userId));
      return queryResponse.decks()
          .stream()
          .map(DeckDto::fromSummary)
          .collect(Collectors.toList());
    });
  }

}
