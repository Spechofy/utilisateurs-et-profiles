package com.spechofy.gestion_utilisateurs.config;

import com.spechofy.gestion_utilisateurs.model.User;
import com.spechofy.gestion_utilisateurs.repository.UserRepository;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import java.io.IOException;

@Component
public class OAuth2LoginSuccessHandler implements AuthenticationSuccessHandler {

    private final UserRepository userRepository;

    public OAuth2LoginSuccessHandler(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication)
            throws IOException, ServletException {

        OidcUser oidcUser = (OidcUser) authentication.getPrincipal();

        String email = oidcUser.getEmail();
        String pseudo = oidcUser.getNickName();
        String firstName = oidcUser.getGivenName();
        String lastName = oidcUser.getFamilyName();
        String birthDate = oidcUser.getBirthdate();
        String phone = oidcUser.getPhoneNumber();

        // Extract roles from the realm_access claim
        Map<String, Object> realmAccess = (Map<String, Object>) oidcUser.getIdToken().getClaim("realm_access");
        List<String> roles = (List<String>) realmAccess.get("roles");

        // Convert roles to GrantedAuthority
        List<SimpleGrantedAuthority> authorities = roles.stream()
                .map(role -> new SimpleGrantedAuthority("ROLE-" + role.toUpperCase()))
                .toList();

        // If the user does not already exist in the database, create a new user
        if (!userRepository.existsByEmail(email)) {
            User user = new User();
            user.setEmail(email);
            user.setPseudo(pseudo);
            user.setFirstName(firstName);
            user.setLastName(lastName);
            if (birthDate != null && !birthDate.isBlank()) {
                user.setBirthDate(LocalDate.parse(birthDate));
            }
            user.setPhone(phone);
            userRepository.save(user);
        }

        // Create a new Authentication object with the authorities
        Authentication newAuthentication = new UsernamePasswordAuthenticationToken(
                oidcUser,
                authentication.getCredentials(),
                authorities
        );

        // Set the new Authentication object in the SecurityContext
        SecurityContextHolder.getContext().setAuthentication(newAuthentication);

        // Redirect the user to the /me page
        response.sendRedirect("/me");
    }
}
