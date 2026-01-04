package com.example.demo.dto;

import jakarta.validation.constraints.*;

public record ProductRequest(
    @NotBlank(message = "Name is required") String name,
    @Min(value = 0, message = "Price cannot be negative") double price,
    String description,
    @Min(value = 0, message = "Quantity cannot be negative") int quantity) {
}
