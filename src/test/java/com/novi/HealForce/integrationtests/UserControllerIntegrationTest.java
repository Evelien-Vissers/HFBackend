package com.novi.HealForce.integrationtests;

import com.novi.dtos.UserInputDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

@SpringBootTest
@AutoConfigureMockMvc
@Sql({"/data.sql"}) // This annotation ensures your test data is loaded before the test runs
public class UserControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testRegisterUserWithUniqueData() throws Exception {
        // Create a UserInputDTO with unique data
        UserInputDTO userInputDTO = new UserInputDTO();
        userInputDTO.setFirstName("Unique");
        userInputDTO.setLastName("User");
        userInputDTO.setEmail("uniqueuser@example.com"); // Unique email to avoid conflicts
        userInputDTO.setPassword("uniquePassword123");

        // Convert the UserInputDTO to JSON
        String requestBody = objectMapper.writeValueAsString(userInputDTO);

        // Perform a POST request to the /users/register endpoint
        mockMvc.perform(post("/users/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isCreated())
                .andExpect(content().string("User registered successfully!"));
    }
}