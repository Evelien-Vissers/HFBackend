package com.novi.HealForce.integrationtests;

import com.novi.dtos.ProfileInputDTO;
import com.novi.dtos.ProfileOutputDTO;
import com.novi.entities.Profile;
import com.novi.repositories.ProfileRepository;
import com.novi.repositories.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;

import java.time.LocalDate;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
@Sql(scripts = "/data.sql")
public class ProfileControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ProfileRepository profileRepository;

    private Profile testProfile;

    @BeforeEach
    void setUp() {
        testProfile = profileRepository.findById(1L).orElseThrow(() ->
                new RuntimeException("Test profile not found in setup"));
    }

    @AfterEach
    void tearDown() {
        profileRepository.deleteAll();
    }

    @Test
    void testGetUserProfileByID() throws Exception {
        // Perform a GET request to retrieve the profile by ID
        mockMvc.perform(get("/profiles/" + testProfile.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.healforceName").value("TestHealer1"))
                .andExpect(jsonPath("$.city").value("TestCity"))
                .andExpect(jsonPath("$.country").value("TestCountry"))
                .andExpect(jsonPath("$.healthChallenge").value("TestDisease"))
                .andExpect(jsonPath("$.diagnosisDate").value("2020-01"));
    }
}
