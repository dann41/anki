package com.dann41.anki.core.cardcollection.application.collectionsimporter;

import com.dann41.anki.shared.application.Command;

import java.util.List;

public record ImportCollectionsCommand(List<CardCollectionRequest> collections) implements Command {
}
