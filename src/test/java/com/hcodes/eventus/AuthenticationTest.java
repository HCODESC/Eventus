package com.hcodes.eventus;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;

import com.hcodes.eventus.entity.UserEntity;
import com.hcodes.eventus.repository.UserRepository;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class AuthenticationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    PasswordEncoder passwordEncoder; 

    @Autowired
    UserRepository userRepository; 

    @BeforeEach
    void setUp() {
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

  
}