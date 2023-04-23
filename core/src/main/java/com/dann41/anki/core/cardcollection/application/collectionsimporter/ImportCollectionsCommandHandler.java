package com.dann41.anki.core.cardcollection.application.collectionsimporter;

import com.dann41.anki.shared.application.CommandHandler;

public class ImportCollectionsCommandHandler implements CommandHandler<ImportCollectionsCommand> {
    private final CardCollectionsImporter cardCollectionsImporter;

    public ImportCollectionsCommandHandler(CardCollectionsImporter cardCollectionsImporter) {
        this.cardCollectionsImporter = cardCollectionsImporter;
    }

    @Override
    public void handle(ImportCollectionsCommand command) {
        cardCollectionsImporter.execute(command);
    }

    @Override
    public Class<ImportCollectionsCommand> supports() {
        return ImportCollectionsCommand.class;
    }
}
