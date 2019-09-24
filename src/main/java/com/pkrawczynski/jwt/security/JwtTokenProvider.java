package com.pkrawczynski.jwt.security;

import com.pkrawczynski.jwt.domain.Role;
import com.pkrawczynski.jwt.exception.JwtValidateException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
public class JwtTokenProvider {

    private final static String SECRET_KEY = "SecretKey";
    private final static long timeToValidate = 3600000;

    private AppUserDetails appUserDetails;

    @Autowired
    public JwtTokenProvider(AppUserDetails appUserDetails) {
        this.appUserDetails = appUserDetails;
    }

    public String createToken(String username, List<Role> roles) {
        Claims claims = Jwts.claims().setSubject(username);
        claims.put("auth", roles.stream()
                .map(s -> new SimpleGrantedAuthority(s.getAuthority()))
                .filter(Objects::nonNull)
                .collect(Collectors.toList()));

        Date dateNow = new Date();
        Date dateExpire = new Date(dateNow.getTime() + timeToValidate);

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(dateNow)
                .setExpiration(dateExpire)
                .signWith(SignatureAlgorithm.HS512, getSecretKeyEncoded())
                .compact();
    }

    public Authentication getAuthentication(String token) {
        UserDetails userDetails = appUserDetails.loadUserByUsername(getUsernameFromToken(token));
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    private String getUsernameFromToken(String token) {
        return Jwts.parser().setSigningKey(getSecretKeyEncoded()).parseClaimsJws(token).getBody().getSubject();
    }

    public String resolveToken(HttpServletRequest req) {
        String bearerToken = req.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(getSecretKeyEncoded()).parseClaimsJws(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            System.out.println(e);
            System.out.println("Invalid token or token expired.");
            throw new JwtValidateException("Invalid token", HttpStatus.FORBIDDEN);
        }
    }

    public String getSecretKeyEncoded() {
        return Base64.getEncoder().encodeToString(SECRET_KEY.getBytes());
    }
}
