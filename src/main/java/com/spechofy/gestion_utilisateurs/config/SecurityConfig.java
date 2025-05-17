package com.spechofy.gestion_utilisateurs.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.beans.factory.annotation.Autowired;


@Configuration
public class SecurityConfig {

    @Autowired
    private OAuth2LoginSuccessHandler oAuth2LoginSuccessHandler;

    @Autowired
    private OidcLogoutSuccessHandler oidcLogoutSuccessHandler;


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers( "/public").permitAll()
                        .requestMatchers("/allusers","/allinfo","/allprofiles").hasAuthority("ROLE-SPECHOFY-MOD")
                        .anyRequest().authenticated()
                )
                .oauth2Login(oauth -> oauth
                        .successHandler(oAuth2LoginSuccessHandler)
                )
                .logout(logout -> logout
                        .logoutSuccessHandler(oidcLogoutSuccessHandler.getHandler())
                );
                /*.oauth2ResourceServer(oauth2 -> oauth2
                        .jwt(Customizer.withDefaults())
                );*/

        return http.build();
    }
}

