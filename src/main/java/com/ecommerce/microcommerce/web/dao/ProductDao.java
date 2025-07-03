package com.ecommerce.microcommerce.web.dao;

import com.ecommerce.microcommerce.web.model.Product;

import java.util.List;

public interface ProductDao {
  List<Product> findAll();

  Product findById(int id);

  Product save(Product product);

  Product update(int id, Product product);

  void delete(int id);
}
