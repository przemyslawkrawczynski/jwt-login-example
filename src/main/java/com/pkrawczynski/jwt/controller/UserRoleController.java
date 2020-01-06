package com.pkrawczynski.jwt.controller;

import com.pkrawczynski.jwt.domain.UserAuthenticationDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/app/user")
@CrossOrigin(origins = "http://localhost:4200")
public class UserRoleController {

    @GetMapping
    public ResponseEntity<UserAuthenticationDto> getAdminPage() {
        UserAuthenticationDto userAuthenticationDto = new UserAuthenticationDto("Jan", "Kowalski");
        return ResponseEntity.ok().body(userAuthenticationDto);
    }
}
