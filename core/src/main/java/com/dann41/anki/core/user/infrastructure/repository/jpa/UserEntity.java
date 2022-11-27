package com.dann41.anki.core.user.infrastructure.repository.jpa;

import com.dann41.anki.core.user.domain.User;
import org.hibernate.annotations.Type;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "authentication")
public class UserEntity {

  @Id
  @Column(name = "user_id")
  private String id;

  @Column(name = "username")
  private String username;

  @Column(columnDefinition = "jsonb")
  @Type(type = "com.vladmihalcea.hibernate.type.json.JsonType")
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
