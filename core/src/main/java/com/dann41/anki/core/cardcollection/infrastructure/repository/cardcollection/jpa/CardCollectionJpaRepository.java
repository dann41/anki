package com.dann41.anki.core.cardcollection.infrastructure.repository.cardcollection.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CardCollectionJpaRepository extends JpaRepository<CardCollectionEntity, String> {
}
