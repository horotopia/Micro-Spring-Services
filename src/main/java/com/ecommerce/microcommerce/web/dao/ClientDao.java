package com.ecommerce.microcommerce.web.dao;

import com.ecommerce.microcommerce.web.model.Client;

import java.util.List;

public interface ClientDao {
  List<Client> findAll();

  Client findById(int id);

  Client save(Client client);

  Client update(int id, Client client);

  void delete(int id);
}
