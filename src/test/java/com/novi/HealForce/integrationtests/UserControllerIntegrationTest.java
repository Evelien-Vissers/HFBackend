package com.novi.HealForce.integrationtests;
import com.novi.dtos.UserOutputDTO;
import com.novi.entities.User;
import com.novi.repositories.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Transactional
class UserControllerIntegrationTest {

    @Autowired
    private WebTestClient webTestClient;

    @Autowired
    private UserRepository userRepository;

    @Test
    void testGetUserById() {
        // Arrange: Creëer en sla een gebruiker op in de in-memory database
        User newUser = new User();
        newUser.setFirstName("John");
        newUser.setLastName("Doe");
        newUser.setEmail("johndoe@example.com");
        userRepository.save(newUser);

        // Act: Voer een GET-aanroep uit om de gebruiker op te halen via de controller
        webTestClient.get()
                .uri("/users/" + newUser.getId())  // Op basis van het gegenereerde ID
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBody(UserOutputDTO.class)  // Controleer of de juiste UserOutputDTO wordt teruggegeven
                .value(userOutputDTO -> {
                    // Assert: Controleer of de gegevens correct zijn
                    assertThat(userOutputDTO.getFirstName()).isEqualTo("John");
                    assertThat(userOutputDTO.getLastName()).isEqualTo("Doe");
                    assertThat(userOutputDTO.getEmail()).isEqualTo("johndoe@example.com");
                });
    }
}


