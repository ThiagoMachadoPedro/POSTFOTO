package io.github.thiagoMachado.extension.security.jwt;

public class InvalidTokenException  extends  RuntimeException {


  public InvalidTokenException(String message) {
    super(message);

  }
}
