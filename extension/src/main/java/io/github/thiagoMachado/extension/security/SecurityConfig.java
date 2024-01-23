package io.github.thiagoMachado.extension.security;



import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import io.github.thiagoMachado.extension.security.jwt.JwtFilter;
import io.github.thiagoMachado.extension.security.jwt.JwtService;
import io.github.thiagoMachado.extension.service.UserServices;


@Configuration
@EnableWebSecurity
public class SecurityConfig {


  @Bean
  public JwtFilter jwtFilter(JwtService jwtService, UserServices userService){
      return new JwtFilter(jwtService, userService);
  }

  @Bean
  public PasswordEncoder passwordEncoder(){
      return new BCryptPasswordEncoder();
  }

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http, JwtFilter jwtFilter) throws Exception {
      return http
              .csrf(AbstractHttpConfigurer::disable)
              .cors(cors -> cors.configure(http))
              .authorizeHttpRequests( auth -> {
                  auth.requestMatchers("/users/**").permitAll();
                  auth.requestMatchers(HttpMethod.GET, "/imagem/**").permitAll();

                  auth.anyRequest().authenticated();
              })
              .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
              .build();
  }

  @Bean
  public CorsConfigurationSource corsConfigurationSource(){
      CorsConfiguration config = new CorsConfiguration().applyPermitDefaultValues();

      UrlBasedCorsConfigurationSource cors = new UrlBasedCorsConfigurationSource();
      cors.registerCorsConfiguration("/**", config);

      return cors;
  }
}
