package com.dann41.anki.core.infrastructure.repository.cardcollection;

import com.dann41.anki.core.domain.card.CardDTO;

import java.util.Collection;

public record CardCollectionDTO(String id, String name, String description, Collection<CardDTO> cards) {
}
