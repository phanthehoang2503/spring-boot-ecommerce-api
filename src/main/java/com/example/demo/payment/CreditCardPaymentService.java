package com.example.demo.payment;

import java.util.Random;

import org.springframework.stereotype.Service;

@Service
public class CreditCardPaymentService implements PaymentService {
  private final Random rd = new Random();

  @Override
  public void processPayment(double amount) {
    if (rd.nextInt(10) < 2) {
      throw new RuntimeException("Payment failed: Insufficient Funds.");
    }
    System.out.println("Charged credit card: $" + amount);
  }
}
