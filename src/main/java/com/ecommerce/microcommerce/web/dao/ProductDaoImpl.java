package com.ecommerce.microcommerce.web.dao;

import com.ecommerce.microcommerce.web.model.Product;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

@Repository
public class ProductDaoImpl implements ProductDao {
  public static List<Product> products = new ArrayList<>();

  static {
    products.add(new Product(1, "Laptop", 1200));
    products.add(new Product(2, "Smartphone", 800));
    products.add(new Product(3, "Tablet", 400));
    products.add(new Product(4, "Smartwatch", 200));
    products.add(new Product(5, "Headphones", 100));
  }

  @Override
  public List<Product> findAll() {
    return products;
  }

  @Override
  public Product findById(int id) {
    for (Product product : products) {
      if (product.getId() == id) {
        return product;
      }
    }
    return null;
  }

  @Override
  public Product save(Product product) {
    if (product.getId() == 0) {
      product.setId(products.size() + 1);
      products.add(product);
    } else {
      for (int i = 0; i < products.size(); i++) {
        if (products.get(i).getId() == product.getId()) {
          products.set(i, product);
          break;
        }
      }
    }
    return product;
  }

  @Override
  public Product update(int id, Product product) {
    for (int i = 0; i < products.size(); i++) {
      if (products.get(i).getId() == id) {
        products.set(i, product);
        return product;
      }
    }
    return null;
  }

  @Override
  public void delete(int id) {
    products.removeIf(product -> product.getId() == id);
  }

}
