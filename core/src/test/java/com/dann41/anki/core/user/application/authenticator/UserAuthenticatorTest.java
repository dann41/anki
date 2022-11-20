package com.dann41.anki.core.user.application.authenticator;

import com.dann41.anki.core.user.domain.UserId;
import com.dann41.anki.core.user.domain.UserMother;
import com.dann41.anki.core.user.domain.UserNotFoundException;
import com.dann41.anki.core.user.domain.UserRepository;
import com.dann41.anki.core.user.infrastructure.domain.PasswordEncoderMatcher;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static com.dann41.anki.core.user.domain.UserMother.USER_ID;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class UserAuthenticatorTest {

  private static final String VALID_PASSWORD = "abcd";
  private static final String INVALID_PASSWORD = "ABCD";

  @Mock
  private UserRepository userRepository;
  private UserAuthenticator userAuthenticator;

  @BeforeEach
  public void setup() {
    userAuthenticator = new UserAuthenticator(userRepository, new PasswordEncoderMatcher(new BCryptPasswordEncoder()));
  }

  @Test
  public void shouldSucceedWhenPasswordIsValid() {
    given(userRepository.findById(new UserId(USER_ID)))
        .willReturn(UserMother.defaultUser());

    userAuthenticator.execute(validPasswordCommand());
  }

  @Test
  public void shouldThrowUserNotFoundExceptionWhenMissingUser() {
    assertThatThrownBy(() -> userAuthenticator.execute(validPasswordCommand()))
        .isInstanceOf(UserNotFoundException.class);
  }

  @Test
  public void shouldThrowUserNotFoundExceptionWhenPasswordInvalid() {
    given(userRepository.findById(new UserId(USER_ID)))
        .willReturn(UserMother.defaultUser());

    assertThatThrownBy(() -> userAuthenticator.execute(invalidPasswordCommand()))
        .isInstanceOf(UserNotFoundException.class);
  }

  private UserAuthenticatorCommand validPasswordCommand() {
    return new UserAuthenticatorCommand(USER_ID, VALID_PASSWORD);
  }

  private UserAuthenticatorCommand invalidPasswordCommand() {
    return new UserAuthenticatorCommand(USER_ID, INVALID_PASSWORD);
  }
}