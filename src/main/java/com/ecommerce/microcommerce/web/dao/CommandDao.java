package com.ecommerce.microcommerce.web.dao;

import com.ecommerce.microcommerce.web.model.Command;

import java.util.List;

public interface CommandDao {

  List<Command> findAll();

  Command findById(int id);

  Command save(Command command);

  Command update(int id, Command command);

  void delete(int id);
}
