package com.example.demo.auth;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.AuthReponse;
import com.example.demo.dto.AuthRequest;

import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("api/auth")
@RequiredArgsConstructor
public class AuthController {
  private final AuthService authService;

  @PostMapping("/register")
  public ResponseEntity<AuthReponse> register(@RequestBody AuthRequest request) {
    return ResponseEntity.ok(authService.register(request));
  }

  @PostMapping("/login")
  public ResponseEntity<AuthReponse> authenticate(
      @RequestBody AuthRequest request) {
    return ResponseEntity.ok(authService.authenticate(request));
  }

}
