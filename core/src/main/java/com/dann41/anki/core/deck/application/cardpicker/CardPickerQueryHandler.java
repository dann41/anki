package com.dann41.anki.core.deck.application.cardpicker;

import com.dann41.anki.shared.application.QueryHandler;

public class CardPickerQueryHandler implements QueryHandler<CardPickerQuery, CardPickerResponse> {
    private final CardPicker cardPicker;

    public CardPickerQueryHandler(CardPicker cardPicker) {
        this.cardPicker = cardPicker;
    }

    @Override
    public CardPickerResponse handle(CardPickerQuery query) {
        return cardPicker.execute(query);
    }

    @Override
    public Class<CardPickerQuery> supports() {
        return CardPickerQuery.class;
    }
}
