package com.example.demo.product;

import java.util.List;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/products")
public class ProductController {
  private final ProductService productService;

  public ProductController(ProductService productService) {
    this.productService = productService;
  }

  @GetMapping
  public List<Product> getAllProducts() {
    return productService.getAllProducts();
  }

  @PostMapping
  public Product createProduct(@Valid @RequestBody Product product) {
    return productService.saveProduct(product);
  }

}
