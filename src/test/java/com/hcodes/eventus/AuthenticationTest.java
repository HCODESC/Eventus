package com.hcodes.eventus;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import com.hcodes.eventus.entity.UserEntity;
import com.hcodes.eventus.repository.UserRepository;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class AuthenticationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    PasswordEncoder passwordEncoder; 

    @Autowired
    UserRepository userRepository; 

    @BeforeEach
    void setUp() {

        userRepository.deleteByUsername("testUser");

        // Ensure user exists in DB
        UserEntity existingUser = userRepository.findByEmail("hcodes3@example.com");
        if (existingUser == null) {
            UserEntity user = new UserEntity();
            user.setUsername("testUser1");
            user.setPassword(passwordEncoder.encode("password3"));
            user.setFirstName("Hector");
            user.setLastName("Castro");
            user.setEmail("hcodes3@example.com");
            user.setRole(UserEntity.Role.CUSTOMER);
            userRepository.save(user);
        }
    }

  

    @Test
    void shouldLoginWithValidCredentials() throws Exception {
        String json = "{\"username\": \"testUser1\", \"password\": \"password3\"}";

        mockMvc.perform(post("/api/auth/login")
                .content(json)
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void shouldNotLoginWithInvalidCredentials() throws Exception {
        String json = "{\"username\": \"testUser1\", \"password\": \"password\"}";

        mockMvc.perform(post("/api/auth/login")
                .content(json)
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isUnauthorized());
    }

    @Test
    void shouldRegisterNewUser() throws Exception {
        String json = "{\"username\": \"testUser2\", \"password\": \"password4\", \"email\": \"test2@test.com\", \"firstName\": \"Test\", \"lastName\": \"User\"}";

        mockMvc.perform(post("/api/auth/register")
                .content(json)
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void shouldNotRegisterExistingUser() throws Exception {
        String json = "{\"username\": \"testUser\", \"password\": \"password4\", \"email\": \"test3@test.com\", \"firstName\": \"Test\", \"lastName\": \"User\"}";

        // First, register the user
        mockMvc.perform(post("/api/auth/register")
                .content(json)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        // Then, try to register again
        mockMvc.perform(post("/api/auth/register")
                .content(json)
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

  
}