package com.pkrawczynski.jwt.security;

import com.pkrawczynski.jwt.domain.Role;
import com.pkrawczynski.jwt.domain.User;
import com.pkrawczynski.jwt.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AppUserDetails implements UserDetailsService {

    private final UserRepository userRepository;

    @Autowired
    public AppUserDetails(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername("jbravo");

        if (user == null) {
            throw new UsernameNotFoundException("User: " + username + " not found in database.");
        }

        //Our app user has only one Role, but User(Security) needs to have List<Role>
        List<Role> roles = new ArrayList<>();
        roles.add(user.getRole());

        return org.springframework.security.core.userdetails.User//
                .withUsername(username)//
                .password(user.getPass())//
                .authorities(roles)//
                .accountExpired(false)//
                .accountLocked(false)//
                .credentialsExpired(false)//
                .disabled(false)//
                .build();
    }
}
