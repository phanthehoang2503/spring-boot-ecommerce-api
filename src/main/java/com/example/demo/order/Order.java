package com.example.demo.order;

import java.time.LocalDateTime;
import java.util.List;
import com.example.demo.product.Product;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "orders")
public class Order {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private LocalDateTime orderDate;
  private Double totalPrice;

  @ManyToMany
  private List<Product> products;
}
