package com.ecommerce.microcommerce.web.dao;

import com.ecommerce.microcommerce.web.model.Command;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

@Repository
public class CommandDaoImpl implements CommandDao {
  public static List<Command> commands = new ArrayList<>();

  static {
    commands.add(new Command(1, "Jean", "Tablette", 2));
    commands.add(new Command(2, "Maxance", "Ordinateur", 1));
    commands.add(new Command(3, "John", "Smartphone", 3));
    commands.add(new Command(4, "Eleo", "Casque Audio", 1));
    commands.add(new Command(5, "Ronald", "Montre Connectée", 1));
  }

  @Override
  public List<Command> findAll() {
    return commands;
  }

  @Override
  public Command findById(int id) {
    for (Command command : commands) {
      if (command.getId() == id) {
        return command;
      }
    }
    return null;
  }

  @Override
  public Command save(Command command) {
    if (command.getId() == 0) {
      command.setId(commands.size() + 1);
      commands.add(command);
    } else {
      for (int i = 0; i < commands.size(); i++) {
        if (commands.get(i).getId() == command.getId()) {
          commands.set(i, command);
          break;
        }
      }
    }
    return command;
  }

  @Override
  public Command update(int id, Command command) {
    for (int i = 0; i < commands.size(); i++) {
      if (commands.get(i).getId() == id) {
        commands.set(i, command);
        return command;
      }
    }
    return null;
  }

  @Override
  public void delete(int id) {
    commands.removeIf(command -> command.getId() == id);
  }
}
