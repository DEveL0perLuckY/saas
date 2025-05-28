package com.backend.app.rest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @GetMapping("/api/welcome")
    public String welcome() {
        return "Welcome to My saas App Backend!";
    }
}
