package com.example.demo.order;

import java.time.LocalDateTime;
import java.util.List;

import com.example.demo.payment.PaymentService;
import com.example.demo.product.Product;
import com.example.demo.product.ProductService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OrderService {
  private final OrderRepository orderRepository;
  private final ProductService productService;
  private final PaymentService paymentService;

  public OrderService(
      OrderRepository orderRepository, ProductService productService, PaymentService paymentService) {
    this.orderRepository = orderRepository;
    this.productService = productService;
    this.paymentService = paymentService;
  }

  @Transactional
  public Order createOrder(List<Long> productIds) {
    // Conversion from ID to Product using Service
    List<Product> products = productService.getProductsByIds(productIds);

    // Calculate Total
    double total = products.stream().mapToDouble(Product::getPrice).sum();

    // Save order information to order object
    Order order = new Order();
    order.setOrderDate(LocalDateTime.now());
    order.setProducts(products);
    order.setTotalPrice(total);
    
    // Save order object to DB
    Order savedOrder = orderRepository.save(order);
    System.out.println("Order saved to DB with ID: "
      + savedOrder.getId());

    // Process Payment
    paymentService.processPayment(total);

    return savedOrder;
  }

  public List<Order> getAllOrders() {
    return orderRepository.findAll();
  }
}
