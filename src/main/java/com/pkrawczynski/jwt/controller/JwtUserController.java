package com.pkrawczynski.jwt.controller;

import com.pkrawczynski.jwt.domain.UserAuthenticationDto;
import com.pkrawczynski.jwt.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class JwtUserController {

    private final UserService userService;

    @Autowired
    public JwtUserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/signin")
    public String login(@RequestBody UserAuthenticationDto userAuthenticationDto) {
        return userService.signin(userAuthenticationDto.getUsername(), userAuthenticationDto.getPassword());
    }
}
