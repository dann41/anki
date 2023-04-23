package com.dann41.anki.auth.infrastructure.auth;

import io.jsonwebtoken.Claims;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import javax.security.auth.Subject;
import java.util.Collection;

public class JwtAuthenticationToken implements Authentication {
  private final Claims claims;
  private final String userId;
  private final String userName;
  private boolean isAuthenticated = false;

  public JwtAuthenticationToken(Claims claims) {
    super();
    this.claims = claims;
    this.userId = claims.get("userId", String.class);
    this.userName = claims.get("userName", String.class);
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return null;
  }

  @Override
  public Object getCredentials() {
    return null;
  }

  @Override
  public Object getDetails() {
    return null;
  }

  @Override
  public Object getPrincipal() {
    return userId;
  }

  @Override
  public boolean isAuthenticated() {
    return isAuthenticated;
  }

  @Override
  public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
    this.isAuthenticated = isAuthenticated;
  }

  @Override
  public String getName() {
    return userName;
  }

  @Override
  public boolean implies(Subject subject) {
    return Authentication.super.implies(subject);
  }

  public Claims claims() {
    return claims;
  }
}
