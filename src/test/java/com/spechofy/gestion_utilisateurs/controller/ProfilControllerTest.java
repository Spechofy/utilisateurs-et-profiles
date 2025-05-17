package com.spechofy.gestion_utilisateurs.controller;

import com.spechofy.gestion_utilisateurs.model.Information;
import com.spechofy.gestion_utilisateurs.model.Profil;
import com.spechofy.gestion_utilisateurs.model.User;
import com.spechofy.gestion_utilisateurs.model.enums.Gender;
import com.spechofy.gestion_utilisateurs.repository.InformationRepository;
import com.spechofy.gestion_utilisateurs.repository.ProfilRepository;
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

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class ProfilControllerTest {

    private MockMvc mockMvc;

    @Mock
    private ProfilRepository profilRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private InformationRepository informationRepository;

    @InjectMocks
    private ProfilController profilController;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(profilController).build();
    }

    @Test
    void testAddOrUpdateProfileToUser_CreatesNewProfile() throws Exception {
        User user = new User();
        user.setUserId(1L);

        Profil profilRequest = new Profil();
        profilRequest.setBiography("New biography");
        profilRequest.setPhoto("photo.jpg");

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(profilRepository.findByUser_UserId(1L)).thenReturn(Optional.empty());

        mockMvc.perform(post("/users/1/addProfile")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"biography\": \"New biography\", \"photo\": \"photo.jpg\"}"))
                .andExpect(status().isOk())
                .andExpect(content().string("Profile created successfully"));
    }

    @Test
    void testAddOrUpdateProfileToUser_UpdatesExistingProfile() throws Exception {
        User user = new User();
        user.setUserId(1L);

        Profil existingProfil = new Profil();
        existingProfil.setProfilId(1L);
        existingProfil.setBiography("Old biography");

        Profil profilRequest = new Profil();
        profilRequest.setBiography("Updated biography");
        profilRequest.setPhoto("updated_photo.jpg");

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(profilRepository.findByUser_UserId(1L)).thenReturn(Optional.of(existingProfil));

        mockMvc.perform(post("/users/1/addProfile")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"biography\": \"Updated biography\", \"photo\": \"updated_photo.jpg\"}"))
                .andExpect(status().isOk())
                .andExpect(content().string("Profile updated successfully"));
    }

    @Test
    void testAddOrUpdateProfileToUser_InvalidUserId() throws Exception {
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        mockMvc.perform(post("/users/1/addProfile")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"biography\": \"New biography\", \"photo\": \"photo.jpg\"}"))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Invalid userId"));
    }

    @Test
    void testGetAllProfiles() throws Exception {
        Profil profil = new Profil();
        profil.setProfilId(1L);
        profil.setBiography("Test biography");

        when(profilRepository.findAll()).thenReturn(List.of(profil));

        mockMvc.perform(get("/allprofiles"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].biography").value("Test biography"));
    }

    @Test
    void testGetAllInformations() throws Exception {
        Information information = new Information();
        information.setAge(30);
        information.setGender(Gender.MAN);

        when(informationRepository.findAll()).thenReturn(List.of(information));

        mockMvc.perform(get("/allinfo"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].age").value(30))
                .andExpect(jsonPath("$[0].gender").value("MAN"));
    }
}
