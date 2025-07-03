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
  public Product getProduct(@PathVariable int id) {
    return productDao.findById(id);
  }

  @PostMapping("/products")
  public Product addProduct(@RequestBody Product product) {
    return productDao.save(product);
  }

  @PutMapping(value = "/products/{id}")
  public Product updateProduct(@PathVariable int id, @RequestBody Product product) {
    Product existingProduct = productDao.findById(id);
    if (existingProduct != null) {
      existingProduct.setName(product.getName());
      existingProduct.setPrice(product.getPrice());
      return productDao.save(product);
    }
    return null;
  }

  @DeleteMapping(value = "/products/{id}")
  public Product deleteProduct(@PathVariable int id) {
    Product product = productDao.findById(id);
    if (product != null) {
      productDao.delete(id);
      return product;
    }
    return null;
  }
}
