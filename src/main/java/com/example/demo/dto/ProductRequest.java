package com.example.demo.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;

public record ProductRequest(
    @Schema(description = "Name of the product", example = "Expensive Banana") @NotBlank(message = "Name is required") String name,

    @Schema(description = "Price in USD", example = "99.99") @Min(value = 0, message = "Price cannot be negative") double price,

    String description,

    @Min(value = 0, message = "Quantity cannot be negative") int quantity) {
}