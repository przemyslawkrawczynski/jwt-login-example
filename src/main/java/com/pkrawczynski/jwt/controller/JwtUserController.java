package com.pkrawczynski.jwt.controller;

import com.pkrawczynski.jwt.domain.JwtTokenDto;
import com.pkrawczynski.jwt.domain.UserAuthenticationDto;
import com.pkrawczynski.jwt.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/users")
@CrossOrigin("*")
public class JwtUserController {

    private final UserService userService;

    @Autowired
    public JwtUserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/signin")
    public JwtTokenDto login(@RequestBody UserAuthenticationDto userAuthenticationDto) {
        return userService.signin(userAuthenticationDto.getUsername(), userAuthenticationDto.getPassword());
    }
}
