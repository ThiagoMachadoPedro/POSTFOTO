package io.github.thiagoMachado.extension.security;

import org.springframework.stereotype.Component;

import lombok.Data;

@Component
@Data
public class CredentialsDTO {

  private String email;
  private String senha;

}
