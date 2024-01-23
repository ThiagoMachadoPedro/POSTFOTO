package io.github.thiagoMachado.extension.security.jwt;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.csrf.InvalidCsrfTokenException;
import org.springframework.web.filter.OncePerRequestFilter;

import io.github.thiagoMachado.extension.model.Users;
import io.github.thiagoMachado.extension.service.UserServices;
import io.jsonwebtoken.io.IOException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UserServices userService;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain) throws ServletException, IOException, java.io.IOException {

        String token = getToken(request);

        if (token != null) {
            try {
                String email = jwtService.getEmailFromToken(token);
                Users user = userService.getEmail(email);
                setUserAsAuthenticated(user);
            } catch (InvalidCsrfTokenException e) {
                log.error("Token inválido: {} ", e.getMessage());
            } catch (Exception e) {
                log.error("Erro na validação do token: {} ", e.getMessage());
            }
        }

        filterChain.doFilter(request, response);
    }

    private void setUserAsAuthenticated(Users user) {
        UserDetails userDetails = org.springframework.security.core.userdetails.User
                .withUsername(user.getEmail())
                .password(user.getSenha())
                .roles("USER")
                .build();

        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, "",
                userDetails.getAuthorities());

        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    private String getToken(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");
        if (authHeader != null) {
            String[] authHeaderParts = authHeader.split(" ");
            if (authHeaderParts.length == 2) {
                return authHeaderParts[1];
            }
        }
        return null;
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        return request.getRequestURI().contains("/users");
    }
}
