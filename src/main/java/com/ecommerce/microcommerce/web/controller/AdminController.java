package com.ecommerce.microcommerce.web.controller;

import com.ecommerce.microcommerce.dao.UserDao;
import com.ecommerce.microcommerce.model.User;
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
}