package com.example.demo.order;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.demo.inventory.InventoryService;
import com.example.demo.payment.PaymentService;
import com.example.demo.product.Product;
import com.example.demo.product.ProductService;

@ExtendWith(MockitoExtension.class)
public class OrderServiceTest {
  @Mock
  private OrderRepository orderRepository;

  @Mock
  private ProductService productService;

  @Mock
  private InventoryService inventoryService;

  @Mock
  private PaymentService paymentService;

  @InjectMocks
  private OrderService orderService;

  @SuppressWarnings("null")
  @Test
  void createOrder_Succeed() {
    // A. Arrange
    Product phone = new Product(1L, "Phone", 100.0, "Desc", 10, false);

    // "When service asks for simple ID 1"
    when(productService.getProductsByIds(List.of(1L))).thenReturn(List.of(phone));

    // "When repo saves anything, just return it back"
    when(orderRepository.save(any(Order.class))).thenAnswer(i -> i.getArguments()[0]);

    // B. Act (Call the real method)
    Order result = orderService.createOrder(List.of(1L));
    // C. Assert (Check results)
    assertEquals(100.0, result.getTotalPrice()); // Price correct?
    verify(paymentService).processPayment(100.0); // Did we pay?
    verify(inventoryService).reduceStock(any()); // Did we reduce stock?
  }

  @SuppressWarnings("null")
  @Test
  void createOrder_Fail_whenPaymentFails() {
    // A. Arrange
    Product phone = new Product(1L, "Phone", 100.0, "Desc", 10, false);
    // "When service asks for simple ID 1"
    when(productService.getProductsByIds(List.of(1L))).thenReturn(List.of(phone));
    when(orderRepository.save(any(Order.class))).thenAnswer(i -> i.getArguments()[0]);

    // "When paying, EXPLODE"
    doThrow(new RuntimeException("No money")).when(paymentService).processPayment(anyDouble());
    // B. Act & Assert
    assertThrows(RuntimeException.class, () -> {
      orderService.createOrder(List.of(1L));
    });
  }
}
