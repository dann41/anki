package com.dann41.anki.core.deck.infrastructure.repository.jpa;

import com.dann41.anki.core.deck.domain.Deck;
import org.hibernate.annotations.Type;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "deck")
public class DeckEntity {

  @Id
  private String id;

  @Column(name = "user_id")
  private String userId;

  @Column(name = "collection_id")
  private String collectionId;

  @Column(columnDefinition = "jsonb")
  @Type(type = "com.vladmihalcea.hibernate.type.json.JsonType")
  private DeckDTO payload;

  private Long payloadVersion;

  public static DeckEntity of(Deck deck) {
    var entity = new DeckEntity();
    entity.id = deck.id().value();
    entity.userId = deck.userId().value();
    entity.collectionId = deck.collectionId();
    entity.payload = DeckDTO.from(deck);
    entity.payloadVersion = 0L;
    return entity;
  }

  public Deck toDomain() {
    return payload.toDomain();
  }
}
