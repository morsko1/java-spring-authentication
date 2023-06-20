package com.example.authentication.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/ping")
public class TestController {
  @GetMapping("")
  public ResponseEntity<String> ping() {
    return ResponseEntity.ok("Pong");
  }
}
