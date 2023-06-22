package com.example.authentication.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.authentication.model.User;
import com.example.authentication.payload.request.LoginRequest;
import com.example.authentication.payload.request.RegisterRequest;
import com.example.authentication.repository.UserRepository;
import com.example.authentication.security.JwtService;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
  @Autowired
  private UserRepository userRepository;

  @Autowired
  private PasswordEncoder passwordEncoder;

  @Autowired
  private AuthenticationManager authenticationManager;

  @Autowired
  private JwtService jwtService;


  @PostMapping("/login")
  public ResponseEntity<?> login(@RequestBody LoginRequest payload) {
    Authentication authentication = authenticationManager.authenticate(
      new UsernamePasswordAuthenticationToken(payload.getUsername(), payload.getPassword())
    );

    SecurityContextHolder.getContext().setAuthentication(authentication);

    UserDetails userPrincipal = (UserDetails) authentication.getPrincipal();

    ResponseCookie cookie = jwtService.generateCookie(userPrincipal);

    return ResponseEntity
      .ok()
      .header(HttpHeaders.SET_COOKIE, cookie.toString())
      .body("Signed in as: " + userPrincipal.getUsername());
  }

  @PostMapping("/register")
  public ResponseEntity<?> register(@RequestBody RegisterRequest payload) {
    if (userRepository.existsByUsername(payload.getUsername())) {
      return ResponseEntity
        .badRequest()
        .body("Error: Username is already taken.");
    }

    User user = new User(payload.getUsername(), passwordEncoder.encode(payload.getPassword()));
    userRepository.save(user);

    return ResponseEntity.ok("Registered username: " + user.getUsername());
  }
}
