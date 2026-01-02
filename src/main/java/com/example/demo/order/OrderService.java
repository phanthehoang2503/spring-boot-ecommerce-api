package com.example.demo.order;

import java.time.LocalDateTime;
import java.util.List;
import com.example.demo.product.Product;
import org.springframework.stereotype.Service;

import com.example.demo.product.ProductRepository;

@Service
public class OrderService {
  private final OrderRepository orderRepository;
  private final ProductRepository productRepository;

  public OrderService(OrderRepository orderRepository, ProductRepository productRepository) {
    this.orderRepository = orderRepository;
    this.productRepository = productRepository;
  }

  public Order createOrder(List<Long> productIds) {
    // Conversion from ID to Product
    List<Product> products = productRepository.findAllById(productIds);

    double total = products.stream().mapToDouble(Product::getPrice).sum();

    Order order = new Order();
    order.setOrderDate(LocalDateTime.now());
    order.setProducts(products);
    order.setTotalPrice(total);

    return orderRepository.save(order);
  }

  public List<Order> getAllOrders() {
    return orderRepository.findAll();
  }
}
