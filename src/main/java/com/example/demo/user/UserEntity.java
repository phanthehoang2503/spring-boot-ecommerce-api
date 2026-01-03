package com.example.demo.user;

import jakarta.persistence.*;

import lombok.Data;

@Entity
@Data
@Table(name = "Users_table")
public class UserEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(unique = true, nullable = false)
  private String username;

  @Column(nullable = false)
  private String password;

  private String role;
}
