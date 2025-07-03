package com.ecommerce.microcommerce.web.controller;

import com.ecommerce.microcommerce.web.model.Client;
import com.ecommerce.microcommerce.web.dao.ClientDao;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ClientController {

  private final ClientDao clientDao;

  public ClientController(ClientDao clientDao) {
    this.clientDao = clientDao;
  }

  @GetMapping("/clients")
  public List<Client> clientsList() {
    return clientDao.findAll();
  }

  @GetMapping("/clients/{id}")
  public Client clientDetails(int id) {
    return clientDao.findById(id);
  }

  @PostMapping("/clients")
  public Client addClient(Client client) {
    return clientDao.save(client);
  }

  @PutMapping("/client/{id}")
  public Client updateClient(@PathVariable int id, Client client) {
    Client existingClient = clientDao.findById(id);
    if (existingClient != null) {
      existingClient.setName(client.getName());
      existingClient.setEmail(client.getEmail());
      return clientDao.save(existingClient);
    }
    return null;
  }

  @DeleteMapping("/client/{id}")
  public Client deleteClient(@PathVariable int id) {
    Client client = clientDao.findById(id);
    if (client != null) {
      clientDao.delete(id);
      return client;
    }
    return null;
  }
}
