package com.example.demo;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
class RootController {
  @GetMapping("/")
  public String ok() { return "API Gateway is up"; }
}