package com.example.demo.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;

public record AddStockRequest(
    @Schema(description = "Number of stock want to add", example = "10") @Min(value = 1, message = "Quantity must be at least 1") int quantity) {
}
