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

  @SuppressWarnings("null")
  public Product saveProduct(Product product) {
    return productRepository.save(product);
  }

  @SuppressWarnings("null")
  public List<Product> getProductsByIds(List<Long> ids) {
    return productRepository.findAllById(ids);
  }
}
