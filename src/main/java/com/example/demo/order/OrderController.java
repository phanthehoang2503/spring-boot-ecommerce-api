package com.example.demo.order;

import java.util.List;

import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

@RestController
@RequestMapping("/api/orders")
public class OrderController {
  private final OrderService orderService;

  public OrderController(OrderService orderService) {
    this.orderService = orderService;
  }

  @PostMapping
  @Operation(summary = "Place a new order", description = "Creates an order for a list of products.")
  @ApiResponse(responseCode = "200", description = "Order placed successfully")
  public Order placeOrder(@RequestBody List<Long> productIds) {
    return orderService.createOrder(productIds);
  }

  @GetMapping
  @Operation(summary = "Get all orders", description = "Retrieves a history of all placed orders.")
  @ApiResponse(responseCode = "200", description = "List of orders retrieved")
  public List<Order> getOrders() {
    return orderService.getAllOrders();
  }
}
