package app.web.zyncky.controller;

import app.web.zyncky.configuration.SecurityConfiguration;
import app.web.zyncky.domain.UserDto;
import app.web.zyncky.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@WebMvcTest(value = {UserApiController.class, SecurityConfiguration.class})
@AutoConfigureMockMvc
class UserApiControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    UserService userService;

    @Test
    void shouldReturn201ForCreateUser() throws Exception {

        when(userService.createUser(any(UserDto.class))).thenReturn(Optional.of(UserDto.builder()
                .uid(UUID.randomUUID().toString()).displayName("Test User").emailAddress("test@gmail.com")
                .roleName("USER").build()));

        String userDtoPayload = """
                {
                "displayName": "Test User",
                "emailAddress": "test@gmail.com",
                "password": "123456"
                }
                """;
        mockMvc.perform(
                        post("/api/users")
                                .content(userDtoPayload)
                                .contentType(MediaType.APPLICATION_JSON)
                ).andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.displayName").value("Test User"))
                .andExpect(jsonPath("$.emailAddress").value("test@gmail.com"))
                .andExpect(jsonPath("$.roleName").value("USER"))
                .andExpect(jsonPath("$.uid").isNotEmpty());


    }

}