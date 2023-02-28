package com.dann41.anki.core.user.infrastructure.repository.jpa;

import com.dann41.anki.core.user.domain.User;
import com.vladmihalcea.hibernate.type.json.JsonType;
import org.hibernate.annotations.Type;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "authentication")
public class UserEntity {

  @Id
  @Column(name = "user_id")
  private String id;

  @Column(name = "username")
  private String username;

  @Column(columnDefinition = "jsonb")
  @Type(value = JsonType.class)
  private UserDTO payload;

  private Long payloadVersion;

  public static UserEntity of(String userId, String username, UserDTO userDTO, Long payloadVersion) {
    var entity = new UserEntity();
    entity.id = userId;
    entity.username = username;
    entity.payload = userDTO;
    entity.payloadVersion = payloadVersion;
    return entity;
  }

  public User toDomain() {
    return payload.toDomain();
  }
}
