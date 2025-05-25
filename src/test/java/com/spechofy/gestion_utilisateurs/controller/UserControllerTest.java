package com.spechofy.gestion_utilisateurs.controller;

import com.spechofy.gestion_utilisateurs.model.User;
import com.spechofy.gestion_utilisateurs.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class UserControllerTest {

    private MockMvc mockMvc;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserController userController;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
    }

    @Test
    void testGetAllUsers() throws Exception {
        User user = new User();
        user.setUserId(1L);
        user.setFirstName("John");
        user.setLastName("Doe");

        when(userRepository.findAll()).thenReturn(List.of(user));

        mockMvc.perform(get("/allusers"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].firstName").value("John"))
                .andExpect(jsonPath("$[0].lastName").value("Doe"));
    }

    @Test
    void testCreateUserWithLocale() throws Exception {
        User user = new User();
        user.setUserId(1L);
        user.setFirstName("John");
        user.setLastName("Doe");
        user.setLocale("fr-FR");

        when(userRepository.save(any(User.class))).thenReturn(user);

        mockMvc.perform(post("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"firstName\":\"John\",\"lastName\":\"Doe\",\"locale\":\"fr-FR\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName").value("John"))
                .andExpect(jsonPath("$.lastName").value("Doe"))
                .andExpect(jsonPath("$.locale").value("fr-FR"));
    }

    @Test
    void testCreateUserWithoutLocale() throws Exception {
        User user = new User();
        user.setUserId(1L);
        user.setFirstName("John");
        user.setLastName("Doe");
        user.setLocale("en-US");

        when(userRepository.save(any(User.class))).thenReturn(user);

        mockMvc.perform(post("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"firstName\":\"John\",\"lastName\":\"Doe\"}")
                .header("Accept-Language", "en-US"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName").value("John"))
                .andExpect(jsonPath("$.lastName").value("Doe"))
                .andExpect(jsonPath("$.locale").value("en-US"));
    }

    @Test
    void testUpdateUserLocale() throws Exception {
        User user = new User();
        user.setUserId(1L);
        user.setFirstName("John");
        user.setLastName("Doe");
        user.setLocale("fr-FR");

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(userRepository.save(any(User.class))).thenReturn(user);

        mockMvc.perform(put("/users/1/locale")
                .contentType(MediaType.APPLICATION_JSON)
                .content("\"fr-FR\""))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.locale").value("fr-FR"));
    }

    @Test
    void testGetUserLocale() throws Exception {
        User user = new User();
        user.setUserId(1L);
        user.setFirstName("John");
        user.setLastName("Doe");
        user.setLocale("fr-FR");

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        mockMvc.perform(get("/users/1/locale"))
                .andExpect(status().isOk())
                .andExpect(content().string("fr-FR"));
    }

    @Test
    void testCreateUserWithLocation() throws Exception {
        User user = new User();
        user.setUserId(1L);
        user.setFirstName("John");
        user.setLastName("Doe");
        user.setLatitude(40.7128);
        user.setLongitude(-74.0060);
        LocalDateTime now = LocalDateTime.now();
        user.setLocationUpdatedAt(now);

        when(userRepository.save(any(User.class))).thenReturn(user);

        mockMvc.perform(post("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"firstName\":\"John\",\"lastName\":\"Doe\",\"latitude\":40.7128,\"longitude\":-74.0060}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName").value("John"))
                .andExpect(jsonPath("$.lastName").value("Doe"))
                .andExpect(jsonPath("$.latitude").value(40.7128))
                .andExpect(jsonPath("$.longitude").value(-74.0060))
                .andExpect(jsonPath("$.locationUpdatedAt").exists());
    }

    @Test
    void testUpdateUserLocation() throws Exception {
        User user = new User();
        user.setUserId(1L);
        user.setFirstName("John");
        user.setLastName("Doe");
        user.setLatitude(40.7128);
        user.setLongitude(-74.0060);
        LocalDateTime now = LocalDateTime.now();
        user.setLocationUpdatedAt(now);

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(userRepository.save(any(User.class))).thenReturn(user);

        mockMvc.perform(put("/users/1/location")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"latitude\":40.7128,\"longitude\":-74.0060}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.latitude").value(40.7128))
                .andExpect(jsonPath("$.longitude").value(-74.0060))
                .andExpect(jsonPath("$.locationUpdatedAt").exists());
    }

    @Test
    void testGetUserLocation() throws Exception {
        User user = new User();
        user.setUserId(1L);
        user.setFirstName("John");
        user.setLastName("Doe");
        user.setLatitude(40.7128);
        user.setLongitude(-74.0060);
        LocalDateTime now = LocalDateTime.now();
        user.setLocationUpdatedAt(now);

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        mockMvc.perform(get("/users/1/location"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.latitude").value(40.7128))
                .andExpect(jsonPath("$.longitude").value(-74.0060))
                .andExpect(jsonPath("$.locationUpdatedAt").exists());
    }
}
