package com.ecommerce.microcommerce.web.controller;

import com.ecommerce.microcommerce.web.model.Client;
import com.ecommerce.microcommerce.web.dao.ClientDao;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class ClientController {

  private final ClientDao clientDao;

  public ClientController(ClientDao clientDao) {
    this.clientDao = clientDao;
  }

  // Lecture accessible à tous les utilisateurs authentifiés
  @GetMapping("/clients")
  @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
  public List<Client> clientsList() {
    return clientDao.findAll();
  }

  @GetMapping("/clients/{id}")
  @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
  public Client clientDetails(@PathVariable String id) {
    Optional<Client> client = clientDao.findById(id);
    return client.orElse(null);
  }

  // Création réservée aux ADMIN
  @PostMapping("/admin/clients")
  @PreAuthorize("hasRole('ADMIN')")
  public Client addClient(@RequestBody Client client) {
    return clientDao.save(client);
  }

  // Modification réservée aux ADMIN
  @PutMapping("/admin/clients/{id}")
  @PreAuthorize("hasRole('ADMIN')")
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

  // Suppression réservée aux ADMIN
  @DeleteMapping("/admin/clients/{id}")
  @PreAuthorize("hasRole('ADMIN')")
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
