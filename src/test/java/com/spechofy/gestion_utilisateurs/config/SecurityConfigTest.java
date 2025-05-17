// package com.spechofy.gestion_utilisateurs.config;

// import com.spechofy.gestion_utilisateurs.controller.UserController;
// import org.junit.jupiter.api.BeforeEach;
// import org.junit.jupiter.api.Test;
// import org.junit.jupiter.api.extension.ExtendWith;
// import org.mockito.junit.jupiter.MockitoExtension;
// import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
// import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
// import org.springframework.boot.test.context.SpringBootTest;
// import org.springframework.context.annotation.Bean;
// import org.springframework.context.annotation.Configuration;
// import org.springframework.security.core.authority.SimpleGrantedAuthority;
// import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
// import org.springframework.security.test.context.support.WithMockUser;
// import org.springframework.test.context.ContextConfiguration;
// import org.springframework.test.context.bean.override.mockito.MockitoBean;
// import org.springframework.test.context.junit.jupiter.SpringExtension;
// import org.springframework.test.context.web.WebAppConfiguration;
// import org.springframework.test.web.servlet.MockMvc;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.test.web.servlet.setup.MockMvcBuilders;
// import org.springframework.web.context.WebApplicationContext;

// import static org.mockito.Mockito.mock;
// import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.oidcLogin;
// import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
// import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
// import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
// import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

// @ExtendWith(MockitoExtension.class)
// @WebMvcTest(controllers = UserController.class)
// class SecurityConfigTest {

//     @Autowired
//     private MockMvc mockMvc;

//     @Test
//     void testPublicEndpointAccessible() throws Exception {
//         mockMvc.perform(get("/public"))
//                 .andExpect(status().isOk())
//                 .andExpect(content().string("what's up"));
//     }

//     @Test
//     void testProtectedEndpointForbiddenWithoutAuth() throws Exception {
//         mockMvc.perform(get("/allusers"))
//                 .andExpect(status().is3xxRedirection());
//     }

//     @Test
//     void testProtectedEndpointAccessibleWithRole() throws Exception {
//         mockMvc.perform(get("/allusers")
//                         .with(oidcLogin()
//                                 .authorities(new SimpleGrantedAuthority("ROLE-SPECHOFY-MOD"))))
//                 .andExpect(status().isOk());
//     }
// }
