package io.github.thiagoMachado.extension.security;

import org.springframework.stereotype.Component;

import io.github.thiagoMachado.extension.model.Users;

@Component
public class UserMapper {

  public Users mapToUsers(UserDTO dto) {

    return new Users()
        .builder()
        .email(dto.getEmail())
        .senha(dto.getSenha())
        .nome(dto.getName())
        .build();

  }
}
