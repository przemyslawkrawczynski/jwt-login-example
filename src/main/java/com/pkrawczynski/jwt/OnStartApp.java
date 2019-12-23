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
        userRepository.save(new User("Mr", "Smith","msmith",encoder.encode("password"), Role.USER));
        User admin = userRepository.findByUsername("jbravo");
        User user = userRepository.findByUsername("msmith");

        String loginUser = user.getUsername();
        String passwordUser = user.getPass();

        String loginAdmin = admin.getUsername();
        String passwordAdmin = admin.getPass();

        System.out.println("Login: " + loginAdmin + " | Pass: " + passwordAdmin);
        System.out.println("Login: " + loginUser + " | Pass: " + passwordUser);
    }
}
