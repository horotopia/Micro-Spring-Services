package com.ecommerce.microcommerce.web.dao;

import com.ecommerce.microcommerce.web.model.Client;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

@Repository
public class ClientDaoImpl implements ClientDao {
    public static List<Client> clients = new ArrayList<>();

    static {
        clients.add(new Client(1, "Alice", "email@yt.fr"));
        clients.add(new Client(2, "Bob", "email@yt.fr"));
        clients.add(new Client(3, "Charlie", "email@yt.fr"));
        clients.add(new Client(4, "Diana", "email@yt.fr"));
        clients.add(new Client(5, "Ethan", "email@yt.fr"));
    }

    @Override
    public List<Client> findAll() {
        return clients;
    }

    @Override
    public Client findById(int id) {
        for (Client client : clients) {
            if (client.getId() == id) {
                return client;
            }
        }
        return null;
    }

    @Override
    public Client save(Client client) {
        if (client.getId() == 0) {
            client.setId(clients.size() + 1);
            clients.add(client);
        } else {
            for (int i = 0; i < clients.size(); i++) {
                if (clients.get(i).getId() == client.getId()) {
                    clients.set(i, client);
                    break;
                }
            }
        }
        return client;
    }

    @Override
    public Client update(int id, Client client) {
        for (int i = 0; i < clients.size(); i++) {
            if (clients.get(i).getId() == id) {
                clients.set(i, client);
                return client;
            }
        }
        return null;
    }

    @Override
    public void delete(int id) {
        clients.removeIf(client -> client.getId() == id);
    }
}
