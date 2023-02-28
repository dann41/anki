package com.dann41.anki.core.cardcollection.infrastructure.repository.cardcollection.jpa;

import com.vladmihalcea.hibernate.type.json.JsonType;
import org.hibernate.annotations.Type;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "collections")
public class CardCollectionEntity {
  @Id
  private String id;

  @Column(columnDefinition = "jsonb")
  @Type(value = JsonType.class)
  private CardCollectionDTO payload;

  private Long payloadVersion;

  public static CardCollectionEntity of(String id, CardCollectionDTO payload, Long payloadVersion) {
    CardCollectionEntity entity = new CardCollectionEntity();
    entity.id = id;
    entity.payload = payload;
    entity.payloadVersion = payloadVersion;
    return entity;
  }

  public CardCollectionDTO payload() {
    return payload;
  }
}
