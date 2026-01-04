package com.example.demo.dto;

import jakarta.validation.constraints.Min;

public record AddStockRequest(@Min(value = 1, message = "Quantity must be at least 1") int quantity) {
}
