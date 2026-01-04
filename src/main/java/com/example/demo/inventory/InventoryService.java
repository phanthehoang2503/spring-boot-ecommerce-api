package com.example.demo.inventory;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.product.Product;
import com.example.demo.product.ProductService;

@Service
public class InventoryService {
  private final ProductService productService;

  public InventoryService(ProductService productService) {
    this.productService = productService;
  }

  @Transactional
  public void reduceStock(List<Product> products) {
    for (Product product : products) {
      // 1. Check
      if (product.getQuantity() < 1) {
        throw new RuntimeException("Out of Stock: " + product.getName());
      }

      // 2. Reduce
      product.setQuantity(product.getQuantity() - 1);

      // 3. Save
      productService.saveProduct(product);
    }
  }
}
