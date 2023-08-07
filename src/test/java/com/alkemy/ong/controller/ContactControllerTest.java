package com.alkemy.ong.controller;

import com.alkemy.ong.dto.ContactDTO;
import com.alkemy.ong.security.service.impl.UserServiceImpl;
import com.alkemy.ong.service.impl.ContactServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static com.alkemy.ong.util.Constants.Endpoints.CONTACT;
import static com.alkemy.ong.util.Constants.ROLE_ADMIN;
import static com.alkemy.ong.util.Constants.ROLE_USER;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
class ContactControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserServiceImpl userService;

    @Mock
    private ContactServiceImpl contactService;

    private ContactDTO contactDTO;

    private List<ContactDTO> contactDTOList;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        contactDTO = new ContactDTO(
                1L,
                "John Doe",
                "0123456789",
                "example@gmail.com",
                "contact example message body");
    }

    @Test
    @WithMockUser(username = "user", authorities = ROLE_USER)
    void Save_contact_as_user() throws Exception{
        when(contactService.save(any(ContactDTO.class)))
                .thenReturn(contactDTO);
        mockMvc.perform(post(CONTACT)
                        .accept(APPLICATION_JSON)
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(contactDTO))
                        .with(user("user").roles("USER"))
                        .with(csrf()))
                .andExpect(status().isOk());
    }

    @Test
    void Save_contact_as_unauthorized_user_is_ok() throws Exception{
        when(contactService.save(any(ContactDTO.class)))
                .thenReturn(contactDTO);
        mockMvc.perform(post(CONTACT)
                        .accept(APPLICATION_JSON)
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(contactDTO))
                        .with(csrf()))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "user", authorities = ROLE_USER)
    void Null_body_should_return_bad_request() throws Exception{
        when(contactService.save(any(ContactDTO.class)))
                .thenReturn(contactDTO);
        mockMvc.perform(post(CONTACT)
                        .accept(APPLICATION_JSON)
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(null))
                        .with(user("user").roles("USER"))
                        .with(csrf()))
                .andExpect(status().isBadRequest());
    }

    @Test
    @WithMockUser(username = "admin", authorities = ROLE_ADMIN)
    void Get_contact_list_as_admin() throws Exception{
        when(contactService.getAll())
                .thenReturn(contactDTOList);
        mockMvc.perform(get(CONTACT)
                        .contentType(APPLICATION_JSON)
                        .with(user("admin").roles("ADMIN"))
                        .with(csrf()))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "user", authorities = ROLE_USER)
    void Get_contact_list_as_user_should_be_forbidden() throws Exception{
        when(contactService.getAll())
                .thenReturn(contactDTOList);
        mockMvc.perform(get(CONTACT)
                        .contentType(APPLICATION_JSON)
                        .with(user("user").roles("USER"))
                        .with(csrf()))
                .andExpect(status().isForbidden());
    }
}