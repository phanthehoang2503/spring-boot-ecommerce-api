package com.example.demo.product;

import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class ProductService {
  private final ProductRepository productRepository;

  public ProductService(ProductRepository pr) {
    this.productRepository = pr;
  }

  public List<Product> getAllProducts() {
    return productRepository.findAll();
  }

  public Product saveProduct(Product product) {
    return productRepository.save(product);
  }
}
