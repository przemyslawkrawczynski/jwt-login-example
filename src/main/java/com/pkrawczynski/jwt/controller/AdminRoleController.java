package com.pkrawczynski.jwt.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/app/admin")
@CrossOrigin(origins = "*")
public class AdminRoleController {

    @GetMapping
    public ResponseEntity<String> getAdminPage(HttpServletRequest request) {
        System.out.println(request.getHeader("Authorization"));
        return ResponseEntity.ok().body("Pomyślnie otworzono stronę admina");
    }
}
