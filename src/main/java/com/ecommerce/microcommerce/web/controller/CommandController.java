package com.ecommerce.microcommerce.web.controller;

import com.ecommerce.microcommerce.web.dao.CommandDao;
import com.ecommerce.microcommerce.web.model.Command;

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
public class CommandController {

  private final CommandDao commandDao;

  public CommandController(CommandDao commandDao) {
    this.commandDao = commandDao;
  }

  @GetMapping("/commands")
  public List<Command> allCommands() {
    return commandDao.findAll();
  }

  @GetMapping("/commands/{id}")
  public Command findById(@PathVariable String id) {
    Optional<Command> command = commandDao.findById(id);
    return command.orElse(null);
  }

  @PostMapping("/commands")
  public Command addCommand(@RequestBody Command command) {
    return commandDao.save(command);
  }

  @PutMapping("/commands/{id}")
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

  @DeleteMapping(value = "/commands/{id}")
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
