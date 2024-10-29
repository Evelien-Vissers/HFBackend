package com.novi.HealForce.integrationtests;

import com.novi.dtos.ProfileInputDTO;
import com.novi.entities.Profile;
import com.novi.repositories.ProfileRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Transactional

class ProfileControllerIntegrationTest {

    @Autowired
    private WebTestClient webTestClient;

    @Autowired
    private ProfileRepository profileRepository;
    private ProfileInputDTO profileInputDTO;

    @BeforeEach
    void setup() {
        // Arrange: Stel de inputgegevens voor het profiel in
        profileInputDTO = new ProfileInputDTO();
        profileInputDTO.setHealforceName("TestUser");
        profileInputDTO.setHealthChallenge("Cancer");
        profileInputDTO.setCity("Amsterdam");
        profileInputDTO.setCountry("Netherlands");
    }

    @Test
    void testCreateProfile() {
        // Act: Voer een POST-aanroep uit om een nieuw profiel aan te maken
        webTestClient.post()
                .uri("/profiles/new")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(profileInputDTO)
                .exchange()
                .expectStatus().isCreated()  // Controleer of de status 201 Created is
                .expectBody(String.class)
                .value(response -> assertThat(response).isEqualTo("Heal Force Profile Successfully Created!"));

        // Assert: Controleer of het profiel correct is opgeslagen in de database
        Profile createdProfile = profileRepository.findAll().get(0);
        assertThat(createdProfile.getHealforceName()).isEqualTo("TestUser");
        assertThat(createdProfile.getHealthChallenge()).isEqualTo("Cancer");
        assertThat(createdProfile.getCity()).isEqualTo("Amsterdam");
        assertThat(createdProfile.getCountry()).isEqualTo("Netherlands");
    }
}



