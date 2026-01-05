package com.example.demo.dto;

import io.swagger.v3.oas.annotations.media.Schema;

public record RegisterRequest(
    @Schema(description = "Username for login", example = "username") String username,
    @Schema(description = "Password for login", example = "password") String password) {
}
