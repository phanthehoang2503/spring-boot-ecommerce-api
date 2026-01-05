package com.example.demo.product;

import java.util.List;
import org.springframework.web.bind.annotation.*;

import com.example.demo.dto.AddStockRequest;
import com.example.demo.dto.ProductRequest;
import com.example.demo.dto.ProductResponse;
import com.example.demo.inventory.InventoryService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;

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
  @Operation(summary = "Get all products", description = "Retrieves a list of all active products.")
  @ApiResponse(responseCode = "200", description = "List of products retrieved")
  public List<ProductResponse> getAllProducts() {
    return productService.getAllProducts();
  }

  @PostMapping
  @Operation(summary = "Create a new product", description = "Adds a new product to the catalog. Returns the created product with ID.")
  @ApiResponse(responseCode = "200", description = "Product created successfully")
  @ApiResponse(responseCode = "400", description = "Invalid input (e.g. negative price)")
  public ProductResponse createProduct(@Valid @RequestBody ProductRequest product) {
    return productService.createProduct(product);
  }

  @PostMapping("/{id}/add-stock")
  @Operation(summary = "Add stock for product with ID", description = "Increases the quantity of a specific product.")
  @ApiResponse(responseCode = "200", description = "Stock added successfully")
  @ApiResponse(responseCode = "404", description = "Product not found")
  public ProductResponse addStock(
      @PathVariable Long id,
      @Valid @RequestBody AddStockRequest request) {
    inventoryService.addStock(id, request.quantity());
    return productService.getProductById(id);
  }

  @DeleteMapping("/{id}")
  @Operation(summary = "Delete a product", description = "Soft deletes a product by ID. It will no longer appear in the catalog.")
  @ApiResponse(responseCode = "200", description = "Product deleted successfully")
  @ApiResponse(responseCode = "404", description = "Product not found")
  public void deleteProduct(@PathVariable Long id) {
    productService.deleteProduct(id);
  }
}
