package com.ecommerce.microcommerce.web.controller;

import com.ecommerce.microcommerce.service.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*")
public class AuthController {

    @Autowired
    private AuthenticationService authenticationService;

    @PostMapping("/register")
    public ResponseEntity<AuthenticationService.AuthenticationResponse> register(
            @RequestBody AuthenticationService.RegisterRequest request) {
        try {
            AuthenticationService.AuthenticationResponse response = authenticationService.register(request);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest()
                    .body(new AuthenticationService.AuthenticationResponse(null, e.getMessage()));
        }
    }

    @PostMapping("/login")
    public ResponseEntity<AuthenticationService.AuthenticationResponse> authenticate(
            @RequestBody AuthenticationService.AuthenticationRequest request) {
        try {
            AuthenticationService.AuthenticationResponse response = authenticationService.authenticate(request);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest()
                    .body(new AuthenticationService.AuthenticationResponse(null, "Invalid credentials"));
        }
    }

    @GetMapping("/test")
    public ResponseEntity<String> test() {
        return ResponseEntity.ok("Auth endpoint is working!");
    }
}