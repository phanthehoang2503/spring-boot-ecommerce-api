package com.example.demo.order;

import java.util.List;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/orders")
public class OrderController {
  private final OrderService orderService;

  public OrderController(OrderService orderService) {
    this.orderService = orderService;
  }

  @PostMapping
  public Order placecOrder(@RequestBody List<Long> productIds) {
    return orderService.createOrder(productIds);
  }

  @org.springframework.web.bind.annotation.GetMapping
  public List<Order> getOrders() {
    return orderService.getAllOrders();
  }
}
