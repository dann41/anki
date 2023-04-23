package com.dann41.anki.api.infrastructure.controllers.login;

import com.dann41.anki.auth.infrastructure.auth.TokenService;
import com.dann41.anki.core.user.application.authenticator.UserAuthenticator;
import com.dann41.anki.core.user.application.authenticator.UserAuthenticatorCommand;
import com.dann41.anki.core.user.application.userfinder.UserFinder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
public class LoginController {

  private final UserAuthenticator userAuthenticator;
  private final UserFinder userFinder;

  private final TokenService tokenService;

  public LoginController(UserAuthenticator userAuthenticator, UserFinder userFinder, TokenService tokenService) {
    this.userAuthenticator = userAuthenticator;
    this.userFinder = userFinder;
    this.tokenService = tokenService;
  }

  @PostMapping("/login")
  public Mono<LoginResponse> login(@RequestBody LoginRequest loginRequest) {
    return Mono.fromCallable(() -> {
      userAuthenticator.execute(new UserAuthenticatorCommand(loginRequest.username(), loginRequest.password()));
      var user = userFinder.execute(loginRequest.username());
      var jwt = tokenService.generate(user.id(), user.username());

      return new LoginResponse(
        user.id(),
        user.username(),
        jwt
      );
    });
  }

}
