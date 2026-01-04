package com.example.demo.user;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.RegisterRequest;
import com.example.demo.dto.UserResponse;

@RestController
@RequestMapping("/api/auth")
public class UserController {
  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;

  public UserController(UserRepository userRepository, PasswordEncoder passwordEncoder) {
    this.userRepository = userRepository;
    this.passwordEncoder = passwordEncoder;
  }

  @PostMapping("/register")
  public UserResponse register(@RequestBody RegisterRequest request) {
    // 1. DTO -> entity
    UserEntity user = new UserEntity();
    user.setUsername(request.username());
    // Hash password
    user.setPassword(passwordEncoder.encode(request.password()));
    user.setRole("USER");

    // 2. Save user
    UserEntity savedUser = userRepository.save(user);

    // 3. Entity -> DTO
    return new UserResponse(
        savedUser.getId(),
        savedUser.getUsername(),
        savedUser.getRole());
  }
}
