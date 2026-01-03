package com.example.demo.payment;

import java.util.Random;

import org.springframework.stereotype.Service;

@Service
public class PaymentService {
  private final Random rd = new Random();

  public void processPayment(Double amount) {
    if (rd.nextInt(10) < 2) {
      throw new RuntimeException("Payment Failed: Insufficient Funds");
    }

    System.out.println("Charged credit card: $" + amount);
  }
}
