package com.example.demo.product;

import java.util.List;
import org.springframework.web.bind.annotation.*;

import com.example.demo.dto.AddStockRequest;
import com.example.demo.dto.ProductRequest;
import com.example.demo.dto.ProductResponse;
import com.example.demo.inventory.InventoryService;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/api/products")
public class ProductController {
  private final ProductService productService;
  private final InventoryService inventoryService;

  public ProductController(ProductService productService, InventoryService inventoryService) {
    this.productService = productService;
    this.inventoryService = inventoryService;
  }

  @GetMapping
  public List<ProductResponse> getAllProducts() {
    return productService.getAllProducts();
  }

  @PostMapping
  public ProductResponse createProduct(@Valid @RequestBody ProductRequest product) {
    return productService.createProduct(product);
  }

  @PostMapping("/{id}/add-stock")
  public ProductResponse addStock(
      @PathVariable Long id,
      @Valid @RequestBody AddStockRequest request) {
    inventoryService.addStock(id, request.quantity());
    return productService.getProductById(id);
  }

  @DeleteMapping("/{id}")
  public void deleteProduct(@PathVariable Long id) {
    productService.deleteProduct(id);
  }
}
