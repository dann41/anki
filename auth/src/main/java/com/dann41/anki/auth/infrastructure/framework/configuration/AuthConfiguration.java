package com.dann41.anki.auth.infrastructure.framework.configuration;

import com.dann41.anki.auth.infrastructure.auth.BearerAuthenticationConverter;
import com.dann41.anki.auth.infrastructure.auth.BearerAuthenticationProvider;
import com.dann41.anki.auth.infrastructure.auth.TokenService;
import com.dann41.anki.core.user.domain.UserRepository;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.ReactiveAuthenticationManagerAdapter;
import org.springframework.security.web.server.authentication.AuthenticationWebFilter;

import java.time.Clock;

@EnableConfigurationProperties
@Configuration
public class AuthConfiguration {

    @Bean
    BearerAuthenticationConverter bearerAuthenticationConverter(TokenService tokenService) {
        return new BearerAuthenticationConverter(tokenService);
    }

    @Bean
    BearerAuthenticationProvider bearerAuthenticationProvider(TokenService tokenService, UserRepository userRepository) {
        return new BearerAuthenticationProvider(tokenService, userRepository);
    }

    @Bean
    AuthenticationWebFilter authenticationWebFilter(
            BearerAuthenticationProvider BearerAuthenticationProvider,
            BearerAuthenticationConverter bearerAuthenticationConverter
    ) {
        AuthenticationWebFilter authenticationWebFilter = new AuthenticationWebFilter(
                new ReactiveAuthenticationManagerAdapter(
                        new ProviderManager(
                                BearerAuthenticationProvider
                        )
                )
        );
        authenticationWebFilter.setServerAuthenticationConverter(bearerAuthenticationConverter);

        return authenticationWebFilter;
    }

    @Bean
    TokenService tokenService(Clock clock, JwtConfig jwtConfig) {
        return new TokenService(clock, jwtConfig.publicKey(), jwtConfig.privateKey());
    }
}
