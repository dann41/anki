package com.dann41.anki.api.infrastructure.configuration;

import com.dann41.anki.api.infrastructure.auth.AuthUser;
import com.dann41.anki.api.infrastructure.auth.BearerAuthenticationConverter;
import com.dann41.anki.api.infrastructure.auth.BearerAuthenticationProvider;
import com.dann41.anki.api.infrastructure.auth.TokenService;
import com.dann41.anki.core.user.domain.User;
import com.dann41.anki.core.user.domain.UserRepository;
import com.dann41.anki.core.user.domain.Username;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.ReactiveAuthenticationManagerAdapter;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.authentication.AuthenticationWebFilter;
import reactor.core.publisher.Mono;

@Configuration
@EnableWebFluxSecurity
public class WebSecurityConfiguration {

    @Bean
    public SecurityWebFilterChain securityWebFilterChain(
            ServerHttpSecurity http,
            AuthenticationWebFilter authenticationWebFilter
    ) {
        http
                .authorizeExchange((exchangeSpec) -> exchangeSpec
                        .pathMatchers("/hello", "/login").permitAll()
                        .anyExchange().authenticated()
                )
                .httpBasic(Customizer.withDefaults())
                .csrf().disable()
                .addFilterBefore(authenticationWebFilter, SecurityWebFiltersOrder.AUTHENTICATION);

        return http.build();
    }

    @Bean
    public ReactiveUserDetailsService reactiveUserDetailsService(UserRepository userRepository) {
        return username -> Mono.fromCallable(() -> {
            User user = userRepository.findByUsername(new Username(username));
            return new AuthUser(user);
        });
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
    BearerAuthenticationConverter bearerAuthenticationConverter(TokenService tokenService) {
        return new BearerAuthenticationConverter(tokenService);
    }

    @Bean
    BearerAuthenticationProvider bearerAuthenticationProvider(TokenService tokenService, UserRepository userRepository) {
        return new BearerAuthenticationProvider(tokenService, userRepository);
    }
}
