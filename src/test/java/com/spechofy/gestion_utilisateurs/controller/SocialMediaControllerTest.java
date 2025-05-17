package com.spechofy.gestion_utilisateurs.controller;

import com.spechofy.gestion_utilisateurs.model.SocialMedia;
import com.spechofy.gestion_utilisateurs.model.User;
import com.spechofy.gestion_utilisateurs.model.UserSocialMedia;
import com.spechofy.gestion_utilisateurs.repository.SocialMediaRepository;
import com.spechofy.gestion_utilisateurs.repository.UserRepository;
import com.spechofy.gestion_utilisateurs.repository.UserSocialMediaRepository;
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
class SocialMediaControllerTest {

    private MockMvc mockMvc;

    @Mock
    private SocialMediaRepository socialMediaRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserSocialMediaRepository userSocialMediaRepository;

    @InjectMocks
    private SocialMediaController socialMediaController;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(socialMediaController).build();
    }

    @Test
    void testGetAllSocialMedias() throws Exception {
        SocialMedia socialMedia = new SocialMedia();
        socialMedia.setSocialMediaId(1L);
        socialMedia.setPlateformeName("Spotify");

        when(socialMediaRepository.findAll()).thenReturn(List.of(socialMedia));

        mockMvc.perform(get("/allsocials"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].plateformeName").value("Spotify"));
    }

    @Test
    void testAddOrUpdateSocialMediaToUser_CreatesNewLink() throws Exception {
        User user = new User();
        user.setUserId(1L);

        SocialMedia socialMedia = new SocialMedia();
        socialMedia.setSocialMediaId(1L);

        UserSocialMedia userSocialMediaRequest = new UserSocialMedia();
        userSocialMediaRequest.setSocialMediaId(1L);
        userSocialMediaRequest.setProfileLink("http://example.com");

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(socialMediaRepository.findById(1L)).thenReturn(Optional.of(socialMedia));
        when(userSocialMediaRepository.findByUserIdAndSocialMediaId(1L, 1L)).thenReturn(Optional.empty());

        mockMvc.perform(post("/users/1/addSocialMedia")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"socialMediaId\": 1, \"profileLink\": \"http://example.com\"}"))
                .andExpect(status().isOk())
                .andExpect(content().string("Social media link added successfully"));
    }

    @Test
    void testAddOrUpdateSocialMediaToUser_UpdatesExistingLink() throws Exception {
        User user = new User();
        user.setUserId(1L);

        SocialMedia socialMedia = new SocialMedia();
        socialMedia.setSocialMediaId(1L);

        UserSocialMedia existingLink = new UserSocialMedia();
        existingLink.setUserId(1L);
        existingLink.setSocialMediaId(1L);
        existingLink.setProfileLink("http://old-link.com");

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(socialMediaRepository.findById(1L)).thenReturn(Optional.of(socialMedia));
        when(userSocialMediaRepository.findByUserIdAndSocialMediaId(1L, 1L)).thenReturn(Optional.of(existingLink));

        mockMvc.perform(post("/users/1/addSocialMedia")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"socialMediaId\": 1, \"profileLink\": \"http://new-link.com\"}"))
                .andExpect(status().isOk())
                .andExpect(content().string("Social media link updated successfully"));
    }

    @Test
    void testAddOrUpdateSocialMediaToUser_InvalidUserId() throws Exception {
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        mockMvc.perform(post("/users/1/addSocialMedia")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"socialMediaId\": 1, \"profileLink\": \"http://example.com\"}"))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Invalid userId"));
    }

    @Test
    void testAddOrUpdateSocialMediaToUser_InvalidSocialMediaId() throws Exception {
        User user = new User();
        user.setUserId(1L);

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(socialMediaRepository.findById(1L)).thenReturn(Optional.empty());

        mockMvc.perform(post("/users/1/addSocialMedia")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"socialMediaId\": 1, \"profileLink\": \"http://example.com\"}"))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Invalid socialMediaId"));
    }
}
