package com.alkemy.ong.controller;

import com.alkemy.ong.dto.AuxUserGetDto;
import com.alkemy.ong.exception.MismatchException;
import com.alkemy.ong.exception.ResourceNotFoundException;
import com.alkemy.ong.security.dto.UserPostDto;
import com.alkemy.ong.security.service.JwtUtils;
import com.alkemy.ong.security.service.impl.UserServiceImpl;
import com.alkemy.ong.util.Constants.Endpoints;
import static com.alkemy.ong.util.Constants.ROLE_ADMIN;
import static com.alkemy.ong.util.Constants.ROLE_USER;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.ArrayList;
import java.util.List;
import static org.hamcrest.CoreMatchers.containsString;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

@WebMvcTest(UserController.class)
public class UserControllerTests {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private UserServiceImpl service;

    @MockBean
    private JwtUtils jwtUtils;

    private static final Logger logger = LoggerFactory.getLogger(UserControllerTests.class);

    private ObjectMapper jsonMapper = new ObjectMapper();

    public static List<AuxUserGetDto> userList;

    public static List<UserPostDto> userPostList;

    @BeforeAll
    public static void setup() {
        userList = generateUserList();
        userPostList = generateUserPostDto();
    }

    @AfterAll
    public static void tearDown() {
        userList = null;
        userPostList = null;
    }

    @Test
    @DisplayName("Get All without authentication test")
    @WithMockUser(username = "user", authorities = ROLE_USER)
    void test_no_admin(TestInfo testInfo) throws Exception {
        logger.info(testInfo.getDisplayName(), "should return 403");
        when(service.getAllAuxUsers()).thenReturn(userList);
        mvc.perform(get(Endpoints.USER)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden())
                .andDo(print());
    }

    @Test
    @DisplayName("Valid get All Users test")
    @WithMockUser(username = "admin", authorities = ROLE_ADMIN)
    void test_get_all_users(TestInfo testInfo) throws Exception {
        logger.info(testInfo.getDisplayName(), "should return 200");
        List<AuxUserGetDto> expected = userList;
        when(service.getAllAuxUsers()).thenReturn(expected);
        mvc.perform(get(Endpoints.USER)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(expected.size()))
                .andExpect(jsonPath("$[?(@.firstName === '" + expected.get(0).getFirstName() + "')]").exists())
                .andExpect(jsonPath("$[?(@.lastName === '" + expected.get(0).getLastName() + "')]").exists())
                .andExpect(jsonPath("$[?(@.email === '" + expected.get(0).getEmail() + "')]").exists())
                .andDo(print());
        verify(service).getAllAuxUsers();
    }

    @Test
    @DisplayName("Valid update test")
    @WithMockUser(username = "user", authorities = ROLE_ADMIN)
    void test_update(TestInfo testInfo) throws Exception {
        logger.info(testInfo.getDisplayName(), "should return 200");
        AuxUserGetDto expected = userList.get(0);
        UserPostDto request = userPostList.get(0);
        when(service.update(any(), any())).thenReturn(expected);
        mvc.perform(
                patch(Endpoints.USER_ID, "1")
                        .content(jsonMapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[?(@.firstName === '" + expected.getFirstName() + "')]").exists())
                .andExpect(jsonPath("$[?(@.lastName === '" + expected.getLastName() + "')]").exists())
                .andDo(print());
    }

    @Test
    @DisplayName("Not found update test")
    @WithMockUser(username = "user", authorities = ROLE_ADMIN)
    void test_update_not_found(TestInfo testInfo) throws Exception {
        logger.info(testInfo.getDisplayName(), "should return 404");

        AuxUserGetDto expected = userList.get(0);
        UserPostDto request = userPostList.get(0);

        when(service.update(any(), any())).thenThrow(new ResourceNotFoundException("User not found"));
        mvc.perform(
                patch(Endpoints.USER_ID, "1")
                        .content(jsonMapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andDo(print());
    }

    @Test
    @DisplayName("Not found delete test")
    @WithMockUser(username = "tomholland@tommail.com", authorities = ROLE_ADMIN)
    void not_found_delete_test(TestInfo testInfo) throws Exception {
        logger.info(testInfo.getDisplayName(), "should return 404");

        AuxUserGetDto expected = userList.get(0);
        UserPostDto request = userPostList.get(0);

        when(service.deleteUser(any())).thenThrow(new ResourceNotFoundException("User not found"));
        mvc.perform(
                delete(Endpoints.USER_ID, "1"))
                .andExpect(status().isNotFound())
                .andDo(print());
    }

    @Test
    @DisplayName("Valid delete test")
    @WithMockUser(username = "tomholland@tommail.com", authorities = ROLE_ADMIN)
    void valid_delete_test(TestInfo testInfo) throws Exception {
        logger.info(testInfo.getDisplayName(), "should return 200");
        AuxUserGetDto response = userList.get(0);
        when(service.deleteUser(any())).thenReturn(response);
        mvc.perform(
                delete(Endpoints.USER_ID, "1")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent())
                .andExpect(content().string(containsString(jsonMapper.writeValueAsString(response))))
                .andDo(print());
    }

    @Test
    @DisplayName("Not authorized delete test")
    @WithMockUser(username = "user", authorities = ROLE_ADMIN)
    void not_authorized_delete_test(TestInfo testInfo) throws Exception {
        logger.info(testInfo.getDisplayName(), "should return 403");

        MismatchException response = new MismatchException("Not authorized, mismatch username");

        when(service.deleteUser(any())).thenThrow(response);
        mvc.perform(
                delete(Endpoints.USER_ID, "1")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden())
                .andDo(print());
    }

    public static List<UserPostDto> generateUserPostDto() {
        ArrayList<UserPostDto> list = new ArrayList<>();
        for (AuxUserGetDto dto : generateUserList()) {
            list.add(new UserPostDto(
                    dto.getFirstName(),
                    dto.getLastName(),
                    dto.getEmail(),
                    "1234Admin1!",
                    "example.png"));
        }
        return list;
    }

    public static List<AuxUserGetDto> generateUserList() {
        ArrayList<AuxUserGetDto> list = new ArrayList<>();

        list.add(new AuxUserGetDto("Tom", "Holland", "tomholland@tommail.com", null));
        list.add(new AuxUserGetDto("Quentin", "Tarantino", "quentin@tarantino.com", null));

        return list;
    }
}
