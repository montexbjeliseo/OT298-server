package com.alkemy.ong.security.controller;

import com.alkemy.ong.exception.AlreadyExistsException;
import com.alkemy.ong.exception.ParameterNotFound;
import com.alkemy.ong.security.dto.AuthRequest;
import com.alkemy.ong.security.dto.UserPostDto;
import com.alkemy.ong.security.service.JwtUtils;
import com.alkemy.ong.security.service.impl.UserServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = AuthController.class)
@AutoConfigureMockMvc
class AuthControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private JwtUtils jwtUtils;

    @MockBean
    private UserServiceImpl userService;

    private UserPostDto validUser;
    private AuthRequest validAuth;

    private static final String REGISTER = "/auth/register";
    private static final String LOGIN = "/auth/login";
    private static final String ME = "/auth/me";

    @BeforeEach
    public void setup(){
        validUser = new UserPostDto(
                "Name",
                "Lastname",
                "user@gmail.com",
                "Admin1234!",
                "photo.jpg");

       validAuth = new AuthRequest(
               "user@gmail.com",
               "Admin1234!");
    }

    @Test
    @DisplayName("Valid User Registration")
    void should_register_an_user() throws Exception {

        mvc.perform(post(REGISTER)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(validUser)))
                .andExpect(status().isCreated())
                .andDo(print());
    }

    @Test
    @DisplayName("Incorrect name format")
    void incorrect_name_format() throws Exception {

        mvc.perform(post(REGISTER)
                        .content(asJsonString(new UserPostDto(
                                "Name12",
                                "Lastname",
                                "user@gmail.com",
                                "Admin1234!",
                                "photo.jpg")))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andDo(print());
    }

    @Test
    @DisplayName("Email must not be blank")
    void email_should_not_be_blank() throws Exception {

        mvc.perform(post(REGISTER)
                        .content(asJsonString(
                                new UserPostDto(
                                        "Name",
                                        "Lastname",
                                        null,
                                        "Admin1234!",
                                        "photo.jpg")))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andDo(print());
    }

    @Test
    @DisplayName("Incorrect password format")
    void incorrect_password_format() throws Exception {

        mvc.perform(post(REGISTER)
                        .content(asJsonString(new UserPostDto(
                                "Name",
                                "Lastname",
                                "user@gmail.com",
                                "Admin",
                                "photo.jpg")))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andDo(print());
    }

    @Test
    @DisplayName("Email already exists")
    void email_should_not_already_exists() throws Exception {

        when(userService.registerUser(any(UserPostDto.class))).thenThrow(AlreadyExistsException.class);

       mvc.perform(post(REGISTER)
                       .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(validUser)))
                .andExpect(status().isBadRequest())
                .andDo(print());
    }

    @Test
    @DisplayName("Login with a valid user")
     void should_sign_in_an_user() throws Exception {

        mvc.perform(post(LOGIN)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(validAuth)))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @DisplayName("Login with a empty username")
    void empty_username_login() throws Exception {

        mvc.perform(post(LOGIN)
                        .content(asJsonString(new AuthRequest(
                                "",
                                "Admin1234!")))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andDo(print());
    }

    @Test
    @DisplayName("Login with a invalid password format")
    void invalid_password_format() throws Exception {

        mvc.perform(post(LOGIN)
                        .content(asJsonString(new AuthRequest(
                                "user@gmail.com",
                                "Admin")))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andDo(print());
    }

    @Test
    @DisplayName("Username doesn't exist")
    void username_must_exist() throws Exception {

        when(userService.authenticate(any(AuthRequest.class))).thenThrow(UsernameNotFoundException.class);

        mvc.perform(post(LOGIN)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(validAuth)))
                .andExpect(status().isNotFound())
                .andDo(print());
    }

    @Test
    @DisplayName("Login with invalid credentials")
    void credentials_must_be_valid() throws Exception {
        when(userService.authenticate(any(AuthRequest.class))).thenThrow(ParameterNotFound.class);

        mvc.perform(post(LOGIN)
                        .content(objectMapper.writeValueAsString(validAuth))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andDo(print());
    }

    @Test
    @DisplayName("Get information from logged user")
    @WithMockUser(roles = {"USER","ADMIN"})
    void should_give_user_logged_information() throws Exception {

        mvc.perform(get(ME).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @DisplayName("User not authenticated")
    @WithAnonymousUser
    void user_must_be_authenticated() throws Exception {

        mvc.perform(get(ME))
                .andExpect(status().isForbidden())
                .andDo(print());
    }

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}