package com.example.FoodDeliveryBackend.infrastructure.security;

import com.example.FoodDeliveryBackend.domain.accountCommands.AddAccountCommand;
import com.example.FoodDeliveryBackend.domain.exception.ErrorType;
import com.example.FoodDeliveryBackend.domain.exception.StructuredError;
import com.example.FoodDeliveryBackend.domain.port.out.KeycloakRegistrationPort;
import com.example.FoodDeliveryBackend.infrastructure.web.dto.auth.*;
import io.vavr.control.Either;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClient;

import java.net.URI;
import java.util.List;

@Service
public class RegistrationService implements KeycloakRegistrationPort {

    private final RestClient restClient;
    private final KeycloakProperties keycloakProperties;

    public RegistrationService(KeycloakProperties keycloakProperties) {
        this.restClient = RestClient.builder().build();
        this.keycloakProperties = keycloakProperties;
    }

    @Override
    public Either<StructuredError, Void> registerCustomer(AddAccountCommand.SaveAccountInput input) {
        RegisterRequest request = new RegisterRequest(input.getUsername(), input.getPassword(),
                input.getEmail(), input.getFirstName(), input.getLastName());

        Either<StructuredError, String> tokenEither = getAdminAccessToken();

        if (tokenEither.isLeft()) {
            return Either.left(tokenEither.getLeft());
        }

        String adminToken = tokenEither.get();

        return createUser(adminToken, request)
                .flatMap(userId ->
                        setPassword(adminToken, userId, request.password())
                                .flatMap(ignored ->
                                        assignRealmRole(adminToken, userId, input.getRole().toString())
                                                .map(ignored2 -> (Void) null)                                )
                );
    }

    private Either<StructuredError, String> getAdminAccessToken() {
        MultiValueMap<String, String> form = new LinkedMultiValueMap<>();
        form.add("grant_type", "password");
        form.add("client_id", keycloakProperties.getAdmin().getClientId());
        form.add("username", keycloakProperties.getAdmin().getUsername());
        form.add("password", keycloakProperties.getAdmin().getPassword());

        return restClient.post()
                .uri(keycloakProperties.adminTokenUrl())
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .body(form)
                .exchange((req, res) -> {

                    if (res.getStatusCode().isError()) {
                        return Either.left(new StructuredError(
                                "Keycloak returned error: " + res.getStatusCode(),
                                ErrorType.SERVER_ERROR
                        ));
                    }

                    AdminTokenResponse body = res.bodyTo(AdminTokenResponse.class);

                    if (body == null || body.accessToken() == null) {
                        return Either.left(new StructuredError(
                                "Invalid Keycloak response",
                                ErrorType.SERVER_ERROR
                        ));
                    }

                    return Either.right(body.accessToken());
                });
    }
    private Either<StructuredError, String> createUser(String adminToken, RegisterRequest request) {
        KeycloakUserCreateRequest body = new KeycloakUserCreateRequest(
                request.username(),
                request.email(),
                request.firstName(),
                request.lastName(),
                true,
                true
        );

        return restClient.post()
                .uri(keycloakProperties.usersAdminUrl())
                .contentType(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + adminToken)
                .body(body)
                .exchange((req, res) -> {
                    if (!res.getStatusCode().equals(HttpStatus.CREATED)) {
                        return Either.left(new StructuredError(
                                "Failed to create user in Keycloak",
                                ErrorType.SERVER_ERROR
                        ));
                    }

                    URI location = res.getHeaders().getLocation();
                    if (location == null) {
                        return Either.left(new StructuredError(
                                "Missing user location header from Keycloak",
                                ErrorType.SERVER_ERROR
                        ));
                    }

                    String path = location.getPath();
                    String userId = path.substring(path.lastIndexOf('/') + 1);

                    return Either.right(userId);
                });
    }

    private Either<StructuredError, Void> assignRealmRole(String adminToken, String userId, String roleName) {
        Either<StructuredError, KeycloakRoleRepresentation> roleEither = restClient.get()
                .uri(keycloakProperties.realmRoleByNameUrl(roleName))
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + adminToken)
                .exchange((req, res) -> {
                    if (res.getStatusCode().isError()) {
                        return Either.left(new StructuredError(
                                "Failed to fetch realm role from Keycloak",
                                ErrorType.SERVER_ERROR
                        ));
                    }

                    KeycloakRoleRepresentation role = res.bodyTo(KeycloakRoleRepresentation.class);

                    if (role == null) {
                        return Either.left(new StructuredError(
                                "Role not found in Keycloak: " + roleName,
                                ErrorType.SERVER_ERROR
                        ));
                    }

                    return Either.right(role);
                });

        if (roleEither.isLeft()) {
            return Either.left(roleEither.getLeft());
        }

        KeycloakRoleRepresentation role = roleEither.get();

        return restClient.post()
                .uri(keycloakProperties.realmRolesUrl(userId))
                .contentType(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + adminToken)
                .body(List.of(role))
                .exchange((req, res) -> {
                    if (res.getStatusCode().isError()) {
                        return Either.left(new StructuredError(
                                "Failed to assign realm role in Keycloak",
                                ErrorType.SERVER_ERROR
                        ));
                    }

                    return Either.right(null);
                });
    }

    private Either<StructuredError, Void> setPassword(String adminToken, String userId, String rawPassword) {
        KeycloakCredential credential = new KeycloakCredential("password", rawPassword, false);

        return restClient.put()
                .uri(keycloakProperties.resetPasswordUrl(userId))
                .contentType(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + adminToken)
                .body(credential)
                .exchange((req, res) -> {
                    if (res.getStatusCode().isError()) {
                        return Either.left(new StructuredError(
                                "Failed to set password in Keycloak",
                                ErrorType.SERVER_ERROR
                        ));
                    }

                    return Either.right(null);
                });
    }
}
