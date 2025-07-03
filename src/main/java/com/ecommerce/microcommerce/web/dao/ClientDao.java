package com.ecommerce.microcommerce.web.dao;

import com.ecommerce.microcommerce.web.model.Client;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientDao extends MongoRepository<Client, String> {
  // Les méthodes de base sont héritées de MongoRepository
  // findAll(), findById(), save(), deleteById() sont automatiquement disponibles
}
