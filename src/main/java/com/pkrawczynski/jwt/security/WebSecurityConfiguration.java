package com.pkrawczynski.jwt.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    AppUserDetails userDetails;

    @Autowired
    JwtTokenFilterConfigurer jwtTokenFilterConfigurer;

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        // Disable CSRF (cross site request forgery)
        http.csrf().disable();

        // No session will be created or used by spring security
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        // Entry points
        http.authorizeRequests()//
                .antMatchers("/users/signin").permitAll()//
                .antMatchers("/users/signup").permitAll()//
                .antMatchers("/h2-console/**/**").permitAll()
                .antMatchers("/app/user").hasAnyAuthority("ADMIN", "USER")
                .antMatchers("/app/admin").hasAnyAuthority("ADMIN")
                // Disallow everything else..
                .anyRequest().authenticated()
                .and()
                .apply(jwtTokenFilterConfigurer);

        // If a user try to access a resource without having enough permissions
        http.exceptionHandling().accessDeniedPage("/users/signin");

    }


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager customAuthenticationManager() throws Exception {
        return authenticationManager();
    }
}
