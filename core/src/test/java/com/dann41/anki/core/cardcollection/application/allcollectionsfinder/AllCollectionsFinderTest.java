package com.dann41.anki.core.cardcollection.application.allcollectionsfinder;

import com.dann41.anki.core.cardcollection.domain.CardCollectionMother;
import com.dann41.anki.core.cardcollection.domain.CardCollectionRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class AllCollectionsFinderTest {

  @Mock
  private CardCollectionRepository cardCollectionRepository;

  @InjectMocks
  private AllCollectionsFinder allCollectionsFinder;

  @Test
  public void shouldReturnAllCollections() {
    given(cardCollectionRepository.findAll())
        .willReturn(List.of(CardCollectionMother.defaultCollection()));

    var response = allCollectionsFinder.execute();

    assertThat(response.collections()).hasSize(1);
    assertThat(response.collections())
        .hasSameElementsAs(Collections.singleton(new CardCollectionSummary("1", "arts", "question regarding art", 6)));
  }

}