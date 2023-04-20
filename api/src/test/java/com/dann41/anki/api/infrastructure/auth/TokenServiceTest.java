package com.dann41.anki.api.infrastructure.auth;

import com.dann41.anki.api.FixtureHelper;
import com.dann41.anki.api.MutableClock;
import org.junit.jupiter.api.Test;

import java.time.Clock;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class TokenServiceTest {

  private static final int MINUTES_BEFORE_EXPIRATION = 15;

  private static final String USER_ID = "3d1844d0-1c8f-4afe-bd8e-0655f8b26d0e";
  private static final String USERNAME = "dani";

  private final String privateKey = FixtureHelper.file("rsa/rsa");
  private final String publicKey = FixtureHelper.file("rsa/rsa_pub");


  @Test
  public void shouldGenerateAndVerifyJwt() {
    var tokenService = new TokenService(Clock.systemUTC(), publicKey, privateKey);

    var token = tokenService.generate(USER_ID, USERNAME);
    var jwtToken = tokenService.verify(token);

    assertThat(jwtToken.claims().get("userId")).isEqualTo(USER_ID);
    assertThat(jwtToken.claims().get("username")).isEqualTo(USERNAME);
  }

  @Test
  public void shouldNotReturnClaimsForExpiredJwt() {
    var clock = new MutableClock(Clock.systemUTC());
    var tokenService = new TokenService(clock, publicKey, privateKey);

    var token = tokenService.generate(USER_ID, USERNAME);

    clock.replaceWith(
        Clock.fixed(
            clock.instant().plus(MINUTES_BEFORE_EXPIRATION, ChronoUnit.MINUTES),
            ZoneId.systemDefault()
        )
    );

    assertThatThrownBy(() -> tokenService.verify(token))
        .isInstanceOf(TokenExpiredException.class);
  }

}