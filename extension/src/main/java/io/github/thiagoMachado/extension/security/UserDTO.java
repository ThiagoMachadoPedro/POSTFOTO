package io.github.thiagoMachado.extension.security;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserDTO {

  private String name;
  private String email;
  private String senha;
}
