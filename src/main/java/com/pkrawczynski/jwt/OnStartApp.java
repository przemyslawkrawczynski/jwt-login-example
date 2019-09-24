package com.pkrawczynski.jwt;

import com.pkrawczynski.jwt.domain.Role;
import com.pkrawczynski.jwt.domain.User;
import com.pkrawczynski.jwt.repository.UserRepository;
import com.pkrawczynski.jwt.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class OnStartApp implements CommandLineRunner {

    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder encoder;

    @Override
    public void run(String... args) throws Exception {
        userRepository.save(new User("Johny", "Bravo", "jbravo", encoder.encode("password"), Role.ADMIN));
        User user = userRepository.findByUsername("jbravo");

        String login = user.getUsername();
        String password = user.getPass();

        System.out.println("Login: " + login + " | Pass: " + password);
    }
}
