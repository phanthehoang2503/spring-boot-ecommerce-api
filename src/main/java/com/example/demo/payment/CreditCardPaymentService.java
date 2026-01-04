package com.example.demo.payment;

import java.util.Random;

import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class CreditCardPaymentService implements PaymentService {
  private final Random rd = new Random();

  @Override
  public void processPayment(double amount) {
    if (rd.nextInt(10) < 2) {
      log.error("Payment failed due to insufficient funds.");
      throw new RuntimeException("Payment failed: Insufficient Funds.");
    }
    log.info("Charged credit card: ${}", amount);
  }
}
