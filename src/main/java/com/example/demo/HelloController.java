package com.example.demo;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

@RestController
public class HelloController {
  @GetMapping("/health")
  @Operation(summary = "Health Check", description = "Simple endpoint to verify server status.")
  @ApiResponse(responseCode = "200", description = "Server is running")
  public String index() {
    return "Running....";
  }
}
