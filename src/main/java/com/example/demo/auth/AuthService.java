package com.example.demo.auth;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.dto.AuthReponse;
import com.example.demo.dto.AuthRequest;
import com.example.demo.user.UserEntity;
import com.example.demo.user.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {
  private final UserRepository repository;
  private final PasswordEncoder passwordEncoder;
  private final JwtService jwtService;
  private final AuthenticationManager authManager;

  public AuthReponse register(AuthRequest request) {
    if (repository.findByUsername(request.username()).isPresent()) {
      throw new RuntimeException("Username already exists");
    }

    var user = new UserEntity();
    user.setUsername(request.username());
    user.setPassword(passwordEncoder.encode(request.password()));
    user.setRole("USER");

    repository.save(user);

    UserDetails userDetails = User.builder()
        .username(user.getUsername())
        .password(user.getPassword())
        .roles(user.getRole())
        .build();
    var jwtToken = jwtService.generateToken(userDetails);
    return new AuthReponse(jwtToken);
  }

  public AuthReponse authenticate(AuthRequest request) {
    var auth = authManager.authenticate(
        new org.springframework.security.authentication.UsernamePasswordAuthenticationToken(
            request.username(),
            request.password()));

    var userDetails = (UserDetails) auth.getPrincipal();
    var jwtToken = jwtService.generateToken(userDetails);
    return new AuthReponse(jwtToken);
  }
}
