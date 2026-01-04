package com.example.demo.order;

import com.example.demo.payment.PaymentService;
import com.example.demo.product.Product;
import com.example.demo.product.ProductRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.Mockito.doThrow;

@SpringBootTest
public class OrderServiceTest {

  @Autowired
  private OrderService orderService;

  @Autowired
  private OrderRepository orderRepository;

  @Autowired
  private ProductRepository productRepository;

  @MockBean
  private PaymentService paymentService;

  @Test
  public void testOrderRollbackOnPaymentFailure() {
    // 1. Setup Data
    Product product = new Product();
    product.setName("Test Product");
    product.setPrice(100.0);
    product.setDescription("Test Desc");
    Product savedProduct = productRepository.save(product);

    // 2. Mock Payment Failure
    doThrow(new RuntimeException("Payment Failed")).when(paymentService).processPayment(anyDouble());

    // 3. Count Orders Before
    long countBefore = orderRepository.count();

    // 4. Attempt Order (Expect Failure)
    assertThrows(RuntimeException.class, () -> {
      orderService.createOrder(List.of(savedProduct.getId()));
    });

    // 5. Verify Rollback (Count should NOT increase)
    long countAfter = orderRepository.count();
    assertEquals(countBefore, countAfter, "Order count should not increase because of rollback!");
  }
}
