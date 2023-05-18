package com.dann41.anki.api.infrastructure.configuration;

import com.dann41.anki.auth.infrastructure.auth.AuthUser;
import com.dann41.anki.core.user.application.userfinder.FindUserByUsernameQuery;
import com.dann41.anki.shared.application.QueryBus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
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
                        .pathMatchers("/hello", "/login", "/signup").permitAll()
                        .anyExchange().authenticated()
                )
                .httpBasic(Customizer.withDefaults())
                .csrf().disable()
                .addFilterBefore(authenticationWebFilter, SecurityWebFiltersOrder.AUTHENTICATION);

        return http.build();
    }

    @Bean
    public ReactiveUserDetailsService reactiveUserDetailsService(QueryBus queryBus) {
        return username -> Mono.fromCallable(() -> {
            var user = queryBus.publish(new FindUserByUsernameQuery(username));
            return new AuthUser(user);
        });
    }
}
