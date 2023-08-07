package com.alkemy.ong.controller;

import com.alkemy.ong.dto.ActivityBasicDTO;
import com.alkemy.ong.dto.ActivityDTO;
import com.alkemy.ong.exception.ResourceNotFoundException;
import com.alkemy.ong.security.service.impl.UserServiceImpl;
import com.alkemy.ong.service.impl.ActivityServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;


import static com.alkemy.ong.util.Constants.ROLE_ADMIN;
import static com.alkemy.ong.util.Constants.ROLE_USER;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@MockitoSettings(strictness = Strictness.LENIENT)
@AutoConfigureMockMvc
public class ActivityControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserServiceImpl service;

    @Mock
    private ActivityServiceImpl activityService;

    private ObjectMapper objectMapper = new ObjectMapper();

    private ObjectWriter objectWriter = objectMapper.writer();

    private ActivityDTO activity;

    private ActivityBasicDTO updateActivity;

    @BeforeEach
    private void init() {
        activity = new ActivityDTO(
                "activity name",
                "activity content",
                "activity.jpg");

        updateActivity = new ActivityBasicDTO(
                "activity name",
                "activity content",
                "activity.jpg");
    }

    @Test
    @DisplayName("Cannot access /activities without credentials")
    public void Activity_creation_without_authorization_is_forbidden() throws Exception {
        postingIsForbidden("/activities");
    }

    @Test
    @DisplayName("An admin user can create an activity")
    @WithMockUser(username = "user", authorities = ROLE_ADMIN)
    public void Activity_creation_with_authentication_is_successful() throws Exception {
        Mockito.when(activityService.save(activity)).thenReturn(activity);

        String content = objectWriter.writeValueAsString(activity);

        MockHttpServletRequestBuilder mockRequest = buildPostRequest("/activities", content);

        mockMvc.perform(mockRequest)
                .andExpect(status().isCreated()).andDo(print());
    }

    @Test
    @DisplayName("A regular user cannot create an activity")
    @WithMockUser(username = "user", authorities = ROLE_USER)
    public void Activity_creation_without_authorities_is_forbidden() throws Exception {
        postingIsForbidden("/activities");
    }

    @Test
    @DisplayName("An invalid activity is not created")
    @WithMockUser(username = "user", authorities = ROLE_ADMIN)
    public void An_invalid_activity_is_not_created() throws Exception {
        ActivityDTO emptyActivity = new ActivityDTO();

        Mockito.when(activityService.save(emptyActivity)).thenReturn(emptyActivity);

        String content = objectWriter.writeValueAsString(emptyActivity);

        MockHttpServletRequestBuilder mockRequest = buildPostRequest("/activities", content);

        mockMvc.perform(mockRequest)
                .andExpect(status().isBadRequest()).andDo(print());
    }


    @Test
    @DisplayName("Cannot access /activities/{id} without credentials")
    public void Activity_update_without_authorization_is_forbidden() throws Exception {
        mockMvc.perform(buildPutRequest("/activities/1", ""));
    }

    @Test
    @DisplayName("A non existent activity cannot be updated")
    @WithMockUser(username = "user", authorities = ROLE_ADMIN)
    public void A_non_existent_activity_cannot_be_updated() throws Exception {
        ActivityBasicDTO nonExistentActivity = new ActivityBasicDTO();

        Mockito.when(activityService.update(1L, nonExistentActivity)).thenThrow(ResourceNotFoundException.class);

        String content = objectWriter.writeValueAsString(nonExistentActivity);

        MockHttpServletRequestBuilder mockRequest = buildPutRequest("/activities/1", content);

        mockMvc.perform(mockRequest)
                .andExpect(status().isNotFound()).andDo(print());
    }

    @Test
    @DisplayName("An existing activity can be updated")
    @WithMockUser(username = "user", authorities = ROLE_ADMIN)
    public void An_existing_activity_can_be_updated() throws Exception {
        Mockito.when(activityService.update(1L, updateActivity)).thenReturn(activity);

        String content = objectWriter.writeValueAsString(activity);

        MockHttpServletRequestBuilder mockRequest = buildPutRequest("/activities/1", content);


        mockMvc.perform(mockRequest)
                .andExpect(status().isOk()).andDo(print());
    }

    private void postingIsForbidden(String endpoint) throws Exception {
        mockMvc.perform(post(endpoint))
                .andExpect(status().isForbidden()).andDo(print());
    }

    private MockHttpServletRequestBuilder buildPutRequest(String endpoint, String content) {
        return put(endpoint)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(content);
    }

    private MockHttpServletRequestBuilder buildPostRequest(String endpoint, String content) {
        return post(endpoint)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(content);
    }
}