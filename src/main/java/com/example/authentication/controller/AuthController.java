package com.example.authentication.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.authentication.payload.request.LoginRequest;
import com.example.authentication.payload.request.RegisterRequest;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
  @PostMapping("/login")
  public ResponseEntity<?> login(@RequestBody LoginRequest payload) {
    return ResponseEntity.ok("Login");
  }

  @PostMapping("/register")
  public ResponseEntity<?> register(@RequestBody RegisterRequest payload) {
    return ResponseEntity.ok("Register");
  }
}
