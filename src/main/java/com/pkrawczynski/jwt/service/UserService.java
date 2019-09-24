package com.pkrawczynski.jwt.service;

import com.pkrawczynski.jwt.domain.Role;
import com.pkrawczynski.jwt.domain.User;
import com.pkrawczynski.jwt.exception.JwtValidateException;
import com.pkrawczynski.jwt.repository.UserRepository;
import com.pkrawczynski.jwt.security.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {
    @Autowired
    private  UserRepository userRepository;
    @Autowired
    private  PasswordEncoder passwordEncoder;
    @Autowired
    private  JwtTokenProvider jwtTokenProvider;
    @Autowired
    private AuthenticationManager authenticationManager;

    public void add(User user) {
        userRepository.save(user);
    }

    public String signin(String username, String password) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
            User user = userRepository.findByUsername(username);
            System.out.println(username);
            return jwtTokenProvider.createToken(username, createRolesList(user.getRole()));
        } catch (AuthenticationException e) {
            throw new JwtValidateException("Invalid username/password supplied", HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }


    public User checkUser(String username) {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new JwtValidateException("The user doesn't exist in database", HttpStatus.NOT_FOUND);
        }
        return user;
    }

    public List<Role> createRolesList(Role role) {
        List<Role> roles = new ArrayList<>();
        roles.add(role);
        return roles;
    }

}
