package com.dann41.anki.api.infrastructure.controllers.login;

import com.dann41.anki.auth.infrastructure.auth.TokenService;
import com.dann41.anki.core.user.application.authenticator.AuthenticateUserCommand;
import com.dann41.anki.core.user.application.userfinder.FindUserByUsernameQuery;
import com.dann41.anki.shared.application.CommandBus;
import com.dann41.anki.shared.application.QueryBus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
public class LoginController {

    private final CommandBus commandBus;
    private final QueryBus queryBus;
    private final TokenService tokenService;

    public LoginController(CommandBus commandBus, QueryBus queryBus, TokenService tokenService) {
        this.commandBus = commandBus;
        this.queryBus = queryBus;
        this.tokenService = tokenService;
    }

    @PostMapping("/login")
    public Mono<LoginResponse> login(@RequestBody LoginRequest loginRequest) {
        return Mono.fromCallable(() -> {
            commandBus.publish(new AuthenticateUserCommand(loginRequest.username(), loginRequest.password()));
            var user = queryBus.publish(new FindUserByUsernameQuery(loginRequest.username()));
            var jwt = tokenService.generate(user.id(), user.username());

            return new LoginResponse(
                    user.id(),
                    user.username(),
                    jwt
            );
        });
    }

}
