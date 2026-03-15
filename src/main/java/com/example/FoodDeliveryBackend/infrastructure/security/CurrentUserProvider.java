package com.example.FoodDeliveryBackend.infrastructure.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;

import java.util.Collection;

@Component
public class CurrentUserProvider {

    public String getUsername() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Jwt jwt = (Jwt) authentication.getPrincipal();
        return jwt.getClaimAsString("preferred_username");
    }

    public String getSubject() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Jwt jwt = (Jwt) authentication.getPrincipal();
        return jwt.getSubject();
    }

    public String getEmail() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Jwt jwt = (Jwt) authentication.getPrincipal();
        return jwt.getClaimAsString("email");
    }

    public Collection<? extends GrantedAuthority> getAuthorities(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.getAuthorities();
    }
}