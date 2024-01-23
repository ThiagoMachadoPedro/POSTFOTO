package io.github.thiagoMachado.extension.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import io.github.thiagoMachado.extension.model.Users;

@Repository
public interface UserRepository extends JpaRepository<Users, String> {

  Users getByEmail(String email);

  Users getBySenha(String senha);


  


}
