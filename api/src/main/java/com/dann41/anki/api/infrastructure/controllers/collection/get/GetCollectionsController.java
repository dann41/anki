package com.dann41.anki.api.infrastructure.controllers.collection.get;

import com.dann41.anki.core.cardcollection.application.allcollectionsfinder.AllCollectionsQuery;
import com.dann41.anki.core.cardcollection.application.allcollectionsfinder.CardCollectionSummary;
import com.dann41.anki.shared.application.QueryBus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.stream.Collectors;

@RestController
public class GetCollectionsController {

    private final QueryBus queryBus;

    public GetCollectionsController(QueryBus queryBus) {
        this.queryBus = queryBus;
    }

    @GetMapping("/api/collections")
    public ResponseEntity<GetCollectionsResponse> getCollections() {
        var response = queryBus.publish(new AllCollectionsQuery());
        return ResponseEntity.ok(
                new GetCollectionsResponse(response.collections()
                        .stream().map(this::toCollectionDto)
                        .collect(Collectors.toList())
                )
        );
    }

    private CollectionDto toCollectionDto(CardCollectionSummary cardCollectionSummary) {
        return new CollectionDto(
                cardCollectionSummary.id(),
                cardCollectionSummary.name(),
                cardCollectionSummary.description(),
                cardCollectionSummary.numberOfQuestions()
        );
    }
}
