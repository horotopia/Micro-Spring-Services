package com.ecommerce.microcommerce.repository;

import com.ecommerce.microcommerce.model.Command;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommandDao extends MongoRepository<Command, String> {
  // Les méthodes de base sont héritées de MongoRepository
  // findAll(), findById(), save(), deleteById() sont automatiquement disponibles
}
