package com.ecommerce.microcommerce.repository;

import com.ecommerce.microcommerce.model.Product;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductDao extends MongoRepository<Product, String> {
  // Les méthodes de base sont héritées de MongoRepository
  // findAll(), findById(), save(), deleteById() sont automatiquement disponibles
}
