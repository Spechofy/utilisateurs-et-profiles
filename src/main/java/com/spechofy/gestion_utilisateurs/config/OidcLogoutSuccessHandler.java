package com.spechofy.gestion_utilisateurs.config;

import org.springframework.security.oauth2.client.oidc.web.logout.OidcClientInitiatedLogoutSuccessHandler;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;

@Component
public class OidcLogoutSuccessHandler {

    private final LogoutSuccessHandler logoutSuccessHandler;

    public OidcLogoutSuccessHandler(ClientRegistrationRepository clientRegistrationRepository) {
        OidcClientInitiatedLogoutSuccessHandler handler =
                new OidcClientInitiatedLogoutSuccessHandler(clientRegistrationRepository);

        handler.setPostLogoutRedirectUri("http://localhost:8082/public");

        this.logoutSuccessHandler = handler;
    }

    public LogoutSuccessHandler getHandler() {
        return logoutSuccessHandler;
    }
}
