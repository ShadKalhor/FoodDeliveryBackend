package com.example.FoodDeliveryBackend.infrastructure.security;

import com.example.FoodDeliveryBackend.infrastructure.web.dto.auth.*;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClient;
import org.springframework.web.server.ResponseStatusException;

import java.net.URI;
import java.util.List;

@Service
public class RegistrationService {

    private final RestClient restClient;
    private final KeycloakProperties keycloakProperties;

    public RegistrationService(KeycloakProperties keycloakProperties) {
        this.restClient = RestClient.builder().build();
        this.keycloakProperties = keycloakProperties;
    }

    public void registerCustomer(RegisterRequest request) {
        String adminToken = getAdminAccessToken();

        String userId = createUser(adminToken, request);
        setPassword(adminToken, userId, request.password());
        assignRealmRole(adminToken, userId, "CUSTOMER");
    }

    private String getAdminAccessToken() {
        MultiValueMap<String, String> form = new LinkedMultiValueMap<>();
        form.add("grant_type", "password");
        form.add("client_id", keycloakProperties.getAdmin().getClientId());
        form.add("username", keycloakProperties.getAdmin().getUsername());
        form.add("password", keycloakProperties.getAdmin().getPassword());

        AdminTokenResponse response = restClient.post()
                .uri(keycloakProperties.adminTokenUrl())
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .body(form)
                .retrieve()
                .onStatus(HttpStatusCode::isError, (req, res) -> {
                    throw new ResponseStatusException(
                            res.getStatusCode(),
                            "Failed to get admin token from Keycloak"
                    );
                })
                .body(AdminTokenResponse.class);

        if (response == null || response.accessToken() == null || response.accessToken().isBlank()) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Admin token was empty");
        }

        return response.accessToken();
    }

    private String createUser(String adminToken, RegisterRequest request) {
        KeycloakUserCreateRequest body = new KeycloakUserCreateRequest(
                request.username(),
                request.email(),
                request.firstName(),
                request.lastName(),
                true,
                true
        );

        var response = restClient.post()
                .uri(keycloakProperties.usersAdminUrl())
                .contentType(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + adminToken)
                .body(body)
                .retrieve()
                .toBodilessEntity();

        if (response.getStatusCode() != HttpStatus.CREATED) {
            throw new ResponseStatusException(
                    response.getStatusCode(),
                    "Failed to create user in Keycloak"
            );
        }

        URI location = response.getHeaders().getLocation();
        if (location == null) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Missing user location header");
        }

        String path = location.getPath();
        return path.substring(path.lastIndexOf('/') + 1);
    }

    private void setPassword(String adminToken, String userId, String rawPassword) {
        KeycloakCredential credential = new KeycloakCredential("password", rawPassword, false);

        restClient.put()
                .uri(keycloakProperties.resetPasswordUrl(userId))
                .contentType(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + adminToken)
                .body(credential)
                .retrieve()
                .onStatus(HttpStatusCode::isError, (req, res) -> {
                    throw new ResponseStatusException(
                            res.getStatusCode(),
                            "Failed to set password in Keycloak"
                    );
                })
                .toBodilessEntity();
    }

    private void assignRealmRole(String adminToken, String userId, String roleName) {
        KeycloakRoleRepresentation role = restClient.get()
                .uri(keycloakProperties.realmRoleByNameUrl(roleName))
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + adminToken)
                .retrieve()
                .onStatus(HttpStatusCode::isError, (req, res) -> {
                    throw new ResponseStatusException(
                            res.getStatusCode(),
                            "Failed to fetch realm role from Keycloak"
                    );
                })
                .body(KeycloakRoleRepresentation.class);

        if (role == null) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Role not found: " + roleName);
        }

        restClient.post()
                .uri(keycloakProperties.realmRolesUrl(userId))
                .contentType(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + adminToken)
                .body(List.of(role))
                .retrieve()
                .onStatus(HttpStatusCode::isError, (req, res) -> {
                    throw new ResponseStatusException(
                            res.getStatusCode(),
                            "Failed to assign realm role in Keycloak"
                    );
                })
                .toBodilessEntity();
    }
}
