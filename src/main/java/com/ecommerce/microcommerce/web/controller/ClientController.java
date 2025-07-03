package com.ecommerce.microcommerce.web.controller;

import com.ecommerce.microcommerce.web.model.Client;
import com.ecommerce.microcommerce.web.dao.ClientDao;

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
  public Client clientDetails(@PathVariable String id) {
    Optional<Client> client = clientDao.findById(id);
    return client.orElse(null);
  }

  @PostMapping("/clients")
  public Client addClient(@RequestBody Client client) {
    return clientDao.save(client);
  }

  @PutMapping("/client/{id}")
  public Client updateClient(@PathVariable String id, @RequestBody Client client) {
    Optional<Client> existingClientOpt = clientDao.findById(id);
    if (existingClientOpt.isPresent()) {
      Client existingClient = existingClientOpt.get();
      existingClient.setName(client.getName());
      existingClient.setEmail(client.getEmail());
      return clientDao.save(existingClient);
    }
    return null;
  }

  @DeleteMapping("/client/{id}")
  public Client deleteClient(@PathVariable String id) {
    Optional<Client> clientOpt = clientDao.findById(id);
    if (clientOpt.isPresent()) {
      Client client = clientOpt.get();
      clientDao.deleteById(id);
      return client;
    }
    return null;
  }
}
