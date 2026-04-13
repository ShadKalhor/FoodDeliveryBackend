package com.example.FoodDeliveryBackend.infrastructure.security;

import com.example.FoodDeliveryBackend.infrastructure.web.dto.auth.LoginRequest;
import com.example.FoodDeliveryBackend.infrastructure.web.dto.auth.LoginResponse;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClient;
import org.springframework.web.server.ResponseStatusException;

@Service
public class AuthService {

    private final RestClient restClient;
    private final KeycloakProperties keycloakProperties;

    public AuthService(KeycloakProperties keycloakProperties) {
        this.restClient = RestClient.builder().build();
        this.keycloakProperties = keycloakProperties;
    }

    public LoginResponse login(LoginRequest request) {
        MultiValueMap<String, String> form = new LinkedMultiValueMap<>();
        form.add("grant_type", "password");
        form.add("client_id", keycloakProperties.getClientId());
        form.add("username", request.getUsername());
        form.add("password", request.getPassword());

        if (keycloakProperties.hasClientSecret()) {
            form.add("client_secret", keycloakProperties.getClientSecret());
        }

        return restClient.post()
                .uri(keycloakProperties.tokenUrl())
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .body(form)
                .retrieve()
                .onStatus(HttpStatusCode::isError, (req, res) -> {
                    throw new ResponseStatusException(
                            res.getStatusCode(),
                            "Invalid username, password, or Keycloak client configuration"
                    );
                })
                .body(LoginResponse.class);
    }
}