package com.dann41.anki.api.infrastructure.controllers.signup;

import com.dann41.anki.core.user.application.userregistrerer.RegisterUserCommand;
import com.dann41.anki.shared.application.CommandBus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SignUpController {

    private final CommandBus commandBus;
    private final PasswordEncoder passwordEncoder;

    public SignUpController(CommandBus commandBus, PasswordEncoder passwordEncoder) {
        this.commandBus = commandBus;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/signup")
    public ResponseEntity<Void> signUp(
            @RequestBody SignUpRequest signUpRequest
    ) {
        commandBus.publish(
                new RegisterUserCommand(
                        signUpRequest.username(),
                        passwordEncoder.encode(signUpRequest.password())
                )
        );
        return ResponseEntity.status(201).build();
    }
}
