package com.example.Teamo.Controller;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.Teamo.DAO.UserDAO;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class AuthController {

    private final UserDAO userDAO;

    public AuthController(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
    return userDAO.findByEmail(request.getEmail())
            .filter(u -> u.getPassword().equals(request.getPassword()))
            .<ResponseEntity<?>>map(u -> ResponseEntity.ok(
                    Map.of("id", u.getId(),
                           "email", u.getEmail(),
                           "firstName", u.getFirstName(),
                           "lastName", u.getLastName())))
            .orElseGet(() -> ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body("Invalid credentials"));
}



    public static class LoginRequest {
        private String email;
        private String password;

        public String getEmail() { return email; }
        public void setEmail(String email) { this.email = email; }

        public String getPassword() { return password; }
        public void setPassword(String password) { this.password = password; }
    }
}