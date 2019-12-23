package com.pkrawczynski.jwt.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserRoleController {

    @GetMapping
    public ResponseEntity<String> getAdminPage() {
        return ResponseEntity.ok().body("Pomyślnie otworzono stronę usera");
    }
}
