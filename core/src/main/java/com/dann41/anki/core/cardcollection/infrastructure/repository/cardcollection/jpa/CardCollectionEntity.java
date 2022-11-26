package com.dann41.anki.core.cardcollection.infrastructure.repository.cardcollection.jpa;

import org.hibernate.annotations.Type;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "collections")
public class CardCollectionEntity {
  @Id
  private String id;

  @Column(columnDefinition = "jsonb")
  @Type(type = "com.vladmihalcea.hibernate.type.json.JsonType")
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
