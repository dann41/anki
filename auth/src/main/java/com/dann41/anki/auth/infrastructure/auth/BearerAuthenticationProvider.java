package com.dann41.anki.auth.infrastructure.auth;

import com.dann41.anki.core.user.application.userfinder.FindUserByIdQuery;
import com.dann41.anki.shared.application.QueryBus;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class BearerAuthenticationProvider implements AuthenticationProvider {

    private final TokenService tokenService;
    private final QueryBus queryBus;

    public BearerAuthenticationProvider(TokenService tokenService, QueryBus queryBus) {
        this.tokenService = tokenService;
        this.queryBus = queryBus;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        var jwtToken = tokenService.verify((String) authentication.getPrincipal());
        var userDetails = retrieveUser((String) jwtToken.getPrincipal(), jwtToken.getName());

        return UsernamePasswordAuthenticationToken.authenticated(
                userDetails,
                userDetails.getPassword(),
                userDetails.getAuthorities()
        );
    }

    private UserDetails retrieveUser(String userId, String userName) throws AuthenticationException {
        var user = queryBus.publish(new FindUserByIdQuery(userId));
        if (user == null) {
            throw new UsernameNotFoundException(userName);
        }
        return new AuthUser(user);
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return BearerToken.class.isAssignableFrom(authentication);
    }
}
