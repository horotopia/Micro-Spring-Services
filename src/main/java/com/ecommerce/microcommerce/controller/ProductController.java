package com.ecommerce.microcommerce.controller;

import com.ecommerce.microcommerce.model.Product;
import com.ecommerce.microcommerce.repository.ProductDao;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class ProductController {

  private final ProductDao productDao;

  public ProductController(ProductDao productDao) {
    this.productDao = productDao;
  }

  // Lecture accessible à tous les utilisateurs authentifiés
  @GetMapping("/products")
  @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
  public List<Product> productsList() {
    return productDao.findAll();
  }

  @GetMapping("/products/{id}")
  @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
  public Product productDetails(@PathVariable String id) {
    Optional<Product> product = productDao.findById(id);
    return product.orElse(null);
  }

  // Création réservée aux ADMIN
  @PostMapping("/admin/products")
  @PreAuthorize("hasRole('ADMIN')")
  public Product addProduct(@RequestBody Product product) {
    return productDao.save(product);
  }

  // Modification réservée aux ADMIN
  @PutMapping("/admin/products/{id}")
  @PreAuthorize("hasRole('ADMIN')")
  public Product updateProduct(@PathVariable String id, @RequestBody Product product) {
    Optional<Product> existingProductOpt = productDao.findById(id);
    if (existingProductOpt.isPresent()) {
      Product existingProduct = existingProductOpt.get();
      existingProduct.setName(product.getName());
      existingProduct.setPrice(product.getPrice());
      return productDao.save(existingProduct);
    }
    return null;
  }

  // Suppression réservée aux ADMIN
  @DeleteMapping("/admin/products/{id}")
  @PreAuthorize("hasRole('ADMIN')")
  public Product deleteProduct(@PathVariable String id) {
    Optional<Product> productOpt = productDao.findById(id);
    if (productOpt.isPresent()) {
      Product product = productOpt.get();
      productDao.deleteById(id);
      return product;
    }
    return null;
  }
}
