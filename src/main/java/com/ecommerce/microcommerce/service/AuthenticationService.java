package com.ecommerce.microcommerce.service;

import com.ecommerce.microcommerce.repository.UserDao;
import com.ecommerce.microcommerce.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private AuthenticationManager authenticationManager;

    public AuthenticationResponse register(RegisterRequest request) {
        // Vérifier si l'utilisateur existe déjà
        if (userDao.existsByUsername(request.getUsername())) {
            throw new RuntimeException("Username already exists");
        }

        if (userDao.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email already exists");
        }

        // Créer un nouvel utilisateur
        var user = new User(
                request.getUsername(),
                passwordEncoder.encode(request.getPassword()),
                request.getEmail(),
                User.Role.USER // Par défaut, nouveau utilisateur = USER
        );

        userDao.save(user);

        var jwtToken = jwtService.generateToken(user);

        return new AuthenticationResponse(jwtToken, "User registered successfully");
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()));

        var user = userDao.findByUsername(request.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found"));

        var jwtToken = jwtService.generateToken(user);

        return new AuthenticationResponse(jwtToken, "Authentication successful");
    }

    // Classes internes pour les requêtes et réponses
    public static class RegisterRequest {
        private String username;
        private String password;
        private String email;

        public RegisterRequest() {
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

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }
    }

    public static class AuthenticationRequest {
        private String username;
        private String password;

        public AuthenticationRequest() {
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

    public static class AuthenticationResponse {
        private String token;
        private String message;

        public AuthenticationResponse(String token, String message) {
            this.token = token;
            this.message = message;
        }

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }
    }
}