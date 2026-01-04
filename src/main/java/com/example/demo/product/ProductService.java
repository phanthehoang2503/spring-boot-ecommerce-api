package com.example.demo.product;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.dto.ProductRequest;
import com.example.demo.dto.ProductResponse;
import com.example.demo.exception.ResourceNotFoundException;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ProductService {
  private final ProductRepository productRepository;

  public ProductService(ProductRepository pr) {
    this.productRepository = pr;
  }

  public List<ProductResponse> getAllProducts() {
    return productRepository.findByDeletedFalse()
        .stream()
        .map(this::mapToResponse)
        .toList();
  }

  public ProductResponse createProduct(ProductRequest request) {
    Product product = new Product();
    product.setName(request.name());
    product.setPrice(request.price());
    product.setDescription(request.description());
    product.setQuantity(request.quantity());

    Product savedProduct = productRepository.save(product);
    log.info("Created new product: {}", product.getName());
    return mapToResponse(savedProduct);
  }

  // Internal method for other services (InventoryService)
  @SuppressWarnings("null")
  public Product saveProduct(Product product) {
    return productRepository.save(product);
  }

  @SuppressWarnings("null")
  public List<Product> getProductsByIds(List<Long> ids) {
    List<Product> prods = productRepository.findAllById(ids).stream()
        .filter(product -> !product.isDeleted())
        .toList();

    if (prods.size() != ids.size()) {
      throw new ResourceNotFoundException("One or more products not found or deleted.");
    }

    return prods;
  }

  public ProductResponse getProductById(Long id) {
    Product prod = getProductEntityById(id);
    return mapToResponse(prod);
  }

  public Product getProductEntityById(Long id) {
    @SuppressWarnings("null")
    Product product = productRepository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + id));

    if (product.isDeleted()) {
      throw new ResourceNotFoundException("Product not found with id: " + id);
    }
    return product;
  }

  public void deleteProduct(Long id) {
    Product prod = getProductEntityById(id);
    prod.setDeleted(true);
    productRepository.save(prod);
  }

  // Helper function
  private ProductResponse mapToResponse(Product product) {
    return new ProductResponse(
        product.getId(),
        product.getName(),
        product.getPrice(),
        product.getDescription(),
        product.getQuantity());
  }
}
