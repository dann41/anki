package com.dann41.anki.core.user.application.userfinder;

import com.dann41.anki.core.user.application.userfinder.UserFinder;
import com.dann41.anki.core.user.application.userfinder.UserResponse;
import com.dann41.anki.core.user.domain.UserId;
import com.dann41.anki.core.user.domain.UserMother;
import com.dann41.anki.core.user.domain.UserNotFoundException;
import com.dann41.anki.core.user.domain.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static com.dann41.anki.core.user.domain.UserMother.USER_ID;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class UserFinderTest {

  @Mock
  private UserRepository userRepository;

  @InjectMocks
  private UserFinder userFinder;

  @Test
  public void shouldReturnUserWithId() {
    given(userRepository.findById(new UserId(USER_ID)))
        .willReturn(UserMother.defaultUser());

    UserResponse userResponse = userFinder.execute(USER_ID);

    assertThat(userResponse.id()).isEqualTo(USER_ID);
  }

  @Test
  public void shouldThrowUserNotFoundException() {
    assertThatThrownBy(() -> userFinder.execute(USER_ID))
        .isInstanceOf(UserNotFoundException.class);
  }
}