package com.example.FoodDeliveryBackend.infrastructure.security;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "app.keycloak")
public class KeycloakProperties {

    private String baseUrl;
    private String realm;
    private String clientId;
    private String clientSecret;

    private Admin admin = new Admin();

    public static class Admin {
        private String realm;
        private String clientId;
        private String username;
        private String password;

        public String getRealm() {
            return realm;
        }

        public void setRealm(String realm) {
            this.realm = realm;
        }

        public String getClientId() {
            return clientId;
        }

        public void setClientId(String clientId) {
            this.clientId = clientId;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }
    }

    public String getBaseUrl() {
        return baseUrl;
    }

    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    public String getRealm() {
        return realm;
    }

    public void setRealm(String realm) {
        this.realm = realm;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getClientSecret() {
        return clientSecret;
    }

    public void setClientSecret(String clientSecret) {
        this.clientSecret = clientSecret;
    }

    public Admin getAdmin() {
        return admin;
    }

    public void setAdmin(Admin admin) {
        this.admin = admin;
    }

    public String tokenUrl() {
        return baseUrl + "/realms/" + realm + "/protocol/openid-connect/token";
    }

    public String adminTokenUrl() {
        return baseUrl + "/realms/" + admin.getRealm() + "/protocol/openid-connect/token";
    }

    public String usersAdminUrl() {
        return baseUrl + "/admin/realms/" + realm + "/users";
    }

    public String userByIdAdminUrl(String userId) {
        return baseUrl + "/admin/realms/" + realm + "/users/" + userId;
    }

    public String resetPasswordUrl(String userId) {
        return userByIdAdminUrl(userId) + "/reset-password";
    }

    public String realmRolesUrl(String userId) {
        return userByIdAdminUrl(userId) + "/role-mappings/realm";
    }

    public String realmRoleByNameUrl(String roleName) {
        return baseUrl + "/admin/realms/" + realm + "/roles/" + roleName;
    }

    public boolean hasClientSecret() {
        return clientSecret != null && !clientSecret.isBlank();
    }
}