package com.example.demo.inventory;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.product.Product;
import com.example.demo.product.ProductService;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
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
      log.info("Reduced stock for Product ID: {}", product.getId());
    }
  }

  @Transactional
  public void addStock(Long productId, int quantity) {
    Product prod = productService.getProductEntityById(productId);

    // Add
    prod.setQuantity(prod.getQuantity() + quantity);

    // Save
    productService.saveProduct(prod);
  }
}
