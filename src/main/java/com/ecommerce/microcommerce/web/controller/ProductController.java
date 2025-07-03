package com.ecommerce.microcommerce.web.controller;

import com.ecommerce.microcommerce.web.dao.ProductDao;
import com.ecommerce.microcommerce.web.model.Product;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
public class ProductController {

  private final ProductDao productDao;

  public ProductController(ProductDao productDao) {
    this.productDao = productDao;
  }

  @GetMapping("/products")
  public List<Product> productsList() {
    return productDao.findAll();
  }

  @GetMapping(value = "/products/{id}")
  public Product getProduct(@PathVariable String id) {
    Optional<Product> product = productDao.findById(id);
    return product.orElse(null);
  }

  @PostMapping("/products")
  public Product addProduct(@RequestBody Product product) {
    return productDao.save(product);
  }

  @PutMapping(value = "/products/{id}")
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

  @DeleteMapping(value = "/products/{id}")
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
