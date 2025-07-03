package com.ecommerce.microcommerce.controller;

import com.ecommerce.microcommerce.repository.UserDao;
import com.ecommerce.microcommerce.model.User;
import com.ecommerce.microcommerce.repository.ClientDao;
import com.ecommerce.microcommerce.model.Client;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
@CrossOrigin(origins = "*")
public class AdminController {

    @Autowired
    private UserDao userDao;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private ClientDao clientDao;

    // Endpoint pour créer un admin par défaut (à utiliser une seule fois)
    @PostMapping("/create-default-admin")
    public ResponseEntity<String> createDefaultAdmin() {
        // Vérifier si un admin existe déjà
        if (userDao.existsByUsername("admin")) {
            return ResponseEntity.badRequest().body("Admin user already exists");
        }

        // Créer l'utilisateur admin par défaut
        User admin = new User(
                "admin",
                passwordEncoder.encode("admin123"),
                "admin@microcommerce.com",
                User.Role.ADMIN);

        userDao.save(admin);

        return ResponseEntity.ok("Default admin created successfully. Username: admin, Password: admin123");
    }

    // Gestion des utilisateurs (réservé aux ADMIN)
    @GetMapping("/users")
    @PreAuthorize("hasRole('ADMIN')")
    public List<User> getAllUsers() {
        return userDao.findAll();
    }

    @DeleteMapping("/users/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> deleteUser(@PathVariable String id) {
        if (userDao.existsById(id)) {
            userDao.deleteById(id);
            return ResponseEntity.ok("User deleted successfully");
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/dashboard")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> adminDashboard() {
        return ResponseEntity.ok("Welcome to Admin Dashboard!");
    }

    // Endpoint pour créer un client avec compte utilisateur (MEILLEURE MÉTHODE)
    @PostMapping("/clients-with-account")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> createClientWithAccount(@RequestBody ClientWithAccountRequest request) {
        try {
            // Vérifier si l'utilisateur existe déjà
            if (userDao.existsByUsername(request.getUsername())) {
                return ResponseEntity.badRequest().body("Username already exists");
            }

            if (userDao.existsByEmail(request.getEmail())) {
                return ResponseEntity.badRequest().body("Email already exists");
            }

            // 1. Créer l'utilisateur avec rôle USER
            User user = new User(
                    request.getUsername(),
                    passwordEncoder.encode(request.getPassword()),
                    request.getEmail(),
                    User.Role.USER);
            user = userDao.save(user);

            // 2. Créer le client correspondant
            Client client = new Client(null, request.getName(), request.getEmail());
            client = clientDao.save(client);

            return ResponseEntity.ok(new ClientWithAccountResponse(
                    "Client et compte créés avec succès",
                    client.getId(),
                    user.getId(),
                    request.getUsername()));

        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Erreur lors de la création: " + e.getMessage());
        }
    }

    // Classes pour les requêtes et réponses
    public static class ClientWithAccountRequest {
        private String name;
        private String email;
        private String username;
        private String password;

        // Constructeurs
        public ClientWithAccountRequest() {
        }

        public ClientWithAccountRequest(String name, String email, String username, String password) {
            this.name = name;
            this.email = email;
            this.username = username;
            this.password = password;
        }

        // Getters et Setters
        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }
    }

    public static class ClientWithAccountResponse {
        private String message;
        private String clientId;
        private String userId;
        private String username;

        public ClientWithAccountResponse(String message, String clientId, String userId, String username) {
            this.message = message;
            this.clientId = clientId;
            this.userId = userId;
            this.username = username;
        }

        // Getters
        public String getMessage() {
            return message;
        }

        public String getClientId() {
            return clientId;
        }

        public String getUserId() {
            return userId;
        }

        public String getUsername() {
            return username;
        }
    }
}