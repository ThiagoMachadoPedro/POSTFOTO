package io.github.thiagoMachado.extension.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.github.thiagoMachado.extension.exception.UsersDuplication;
import io.github.thiagoMachado.extension.model.Users;
import io.github.thiagoMachado.extension.repository.UserRepository;
import io.github.thiagoMachado.extension.security.AcessoToken;
import io.github.thiagoMachado.extension.security.jwt.JwtService;

@Service
public class UserServices {

  @Autowired
  private UserRepository repository;

  @Autowired
  private PasswordEncoder enconde;

  @Autowired
  private JwtService jwtService;

  @Transactional
  public Users save(Users user) {
    var posible = getEmail(user.getEmail());
    if (posible != null) {
      throw new UsersDuplication("Usuario ja existe no sistema");
    }
    encodePassword(user);

    return repository.save(user);
  }

  public Users getEmail(String email) {
    return repository.getByEmail(email);
  }

  public AcessoToken autheticate(String email, String senha) {

   var user= getEmail(email);

   if(user == null){
      return null;
   }
      boolean matches = enconde.matches(senha , user.getSenha());
      if(matches){
        return jwtService.generateToken(user);
      }
    return null;
  }

  private void encodePassword(Users user){
    String rawPassword = user.getSenha();
    String encodedPassword = enconde.encode(rawPassword);
    user.setSenha(encodedPassword);
}

}
