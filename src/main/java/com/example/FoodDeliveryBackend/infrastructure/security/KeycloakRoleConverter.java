package com.example.FoodDeliveryBackend.infrastructure.security;

import org.springframework.core.convert.converter.Converter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class KeycloakRoleConverter implements Converter<Jwt, Collection<GrantedAuthority>> {

    private static final Set<String> APP_ROLES = Set.of("ADMIN", "CUSTOMER", "DRIVER");

    @Override
    public Collection<GrantedAuthority> convert(Jwt jwt) {
        Map<String, Object> realmAccess = jwt.getClaim("realm_access");

        if (realmAccess == null || realmAccess.isEmpty()) {
            return Collections.emptyList();
        }

        Object rolesObject = realmAccess.get("roles");
        if (!(rolesObject instanceof List<?> roles)) {
            return Collections.emptyList();
        }

        return roles.stream()
                .filter(String.class::isInstance)
                .map(String.class::cast)
                .filter(APP_ROLES::contains)
                .map(role -> "ROLE_" + role)
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }
}