package com.ecommerce.microcommerce.controller;

import com.ecommerce.microcommerce.model.Command;
import com.ecommerce.microcommerce.repository.CommandDao;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class CommandController {

  private final CommandDao commandDao;

  public CommandController(CommandDao commandDao) {
    this.commandDao = commandDao;
  }

  // Lecture accessible à tous les utilisateurs authentifiés
  @GetMapping("/commands")
  @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
  public List<Command> commandsList() {
    return commandDao.findAll();
  }

  @GetMapping("/commands/{id}")
  @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
  public Command commandDetails(@PathVariable String id) {
    Optional<Command> command = commandDao.findById(id);
    return command.orElse(null);
  }

  // Création accessible aux utilisateurs authentifiés
  @PostMapping("/commands")
  @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
  public Command addCommand(@RequestBody Command command) {
    return commandDao.save(command);
  }

  // Modification réservée aux ADMIN
  @PutMapping("/admin/commands/{id}")
  @PreAuthorize("hasRole('ADMIN')")
  public Command updateCommand(@PathVariable String id, @RequestBody Command command) {
    Optional<Command> existingCommandOpt = commandDao.findById(id);
    if (existingCommandOpt.isPresent()) {
      Command existingCommand = existingCommandOpt.get();
      existingCommand.setCustomerName(command.getCustomerName());
      existingCommand.setProductName(command.getProductName());
      existingCommand.setQuantity(command.getQuantity());
      return commandDao.save(existingCommand);
    }
    return null;
  }

  // Suppression réservée aux ADMIN
  @DeleteMapping("/admin/commands/{id}")
  @PreAuthorize("hasRole('ADMIN')")
  public Command deleteCommand(@PathVariable String id) {
    Optional<Command> commandOpt = commandDao.findById(id);
    if (commandOpt.isPresent()) {
      Command command = commandOpt.get();
      commandDao.deleteById(id);
      return command;
    }
    return null;
  }
}
