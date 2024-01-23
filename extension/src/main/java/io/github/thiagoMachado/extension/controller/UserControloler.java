package io.github.thiagoMachado.extension.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.github.thiagoMachado.extension.model.Users;
import io.github.thiagoMachado.extension.security.CredentialsDTO;
import io.github.thiagoMachado.extension.security.UserDTO;
import io.github.thiagoMachado.extension.security.UserMapper;
import io.github.thiagoMachado.extension.service.UserServices;

@RestController
@RequestMapping("/users")
public class UserControloler {

  @Autowired
  private UserServices service;

  @Autowired
  private UserMapper mapper;

  @PostMapping
  public ResponseEntity<?> save(@RequestBody UserDTO dto) throws Exception {
    try {
      Users user = mapper.mapToUsers(dto);
      service.save(user);
      return ResponseEntity.status(HttpStatus.CREATED).build();

    } catch (Exception e) {
      Map<String, String> jsonResult = Map.of("error", e.getMessage());
      return ResponseEntity.status(HttpStatus.CONFLICT).body(jsonResult);
    }
  }

  @PostMapping(value = "/auth")
  public ResponseEntity<?> authenticate(@RequestBody CredentialsDTO credential) {

    var token = service.autheticate(credential.getEmail(), credential.getSenha());
    if (token == null) {
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    return ResponseEntity.ok(token);
  }

}
