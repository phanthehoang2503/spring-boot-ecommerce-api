package com.example.demo.dto;

public record ProductResponse(
    Long id,
    String name,
    double price,
    String description,
    int quantity) {
}
