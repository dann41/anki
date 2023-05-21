package com.dann41.anki.api.infrastructure.controllers.collection.importer;

import com.dann41.anki.core.cardcollection.application.collectionsimporter.CardCollectionRequest;
import com.dann41.anki.core.cardcollection.application.collectionsimporter.ImportCollectionsCommand;
import com.dann41.anki.shared.application.CommandBus;
import com.dann41.anki.shared.application.FilenameUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.Collections;
import java.util.UUID;

@RestController
public class ImportCollectionController {

    private final CommandBus commandBus;

    public ImportCollectionController(CommandBus commandBus) {
        this.commandBus = commandBus;
    }

    @PostMapping(value = "/api/collections/import")
    public Mono<ResponseEntity<String>> importCollection(@RequestPart("collection") FilePart file) {
        var reader = new FileCollectionReader();
        var collectionId = UUID.randomUUID().toString();

        return reader.readCards(file.content())
                .map(collection -> {
                            commandBus.publish(new ImportCollectionsCommand(
                                    Collections.singletonList(
                                            new CardCollectionRequest(
                                                    collectionId,
                                                    FilenameUtils.removeExtension(file.filename()),
                                                    collection
                                            )
                                    )
                            ));

                            return "{\"collectionId\":\"" + collectionId + "\"}";
                        }
                )
                .map(responseBody -> ResponseEntity.status(201).body(responseBody));
    }

}
