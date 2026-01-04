package com.example.demo.order;

import java.time.LocalDateTime;
import java.util.List;

import com.example.demo.inventory.InventoryService;
import com.example.demo.payment.PaymentService;
import com.example.demo.product.Product;
import com.example.demo.product.ProductService;

import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
public class OrderService {
  private final OrderRepository orderRepository;
  private final ProductService productService;
  private final PaymentService paymentService;
  private final InventoryService inventoryService;

  public OrderService(
      OrderRepository orderRepository,
      ProductService productService,
      PaymentService paymentService,
      InventoryService inventoryService) {
    this.orderRepository = orderRepository;
    this.productService = productService;
    this.paymentService = paymentService;
    this.inventoryService = inventoryService;
  }

  @Transactional
  public Order createOrder(List<Long> productIds) {
    // Conversion from ID to Product using Service
    List<Product> products = productService.getProductsByIds(productIds);

    // Reduce Stock
    inventoryService.reduceStock(products);

    // Calculate Total
    double total = products.stream().mapToDouble(Product::getPrice).sum();

    // Save order information to order object
    Order order = new Order();
    order.setOrderDate(LocalDateTime.now());
    order.setProducts(products);
    order.setTotalPrice(total);

    // Save order object to DB
    Order savedOrder = orderRepository.save(order);
    log.info("Order saved to DB with ID: {}", savedOrder.getId());

    // Process Payment
    paymentService.processPayment(total);

    return savedOrder;
  }

  public List<Order> getAllOrders() {
    return orderRepository.findAll();
  }
}
