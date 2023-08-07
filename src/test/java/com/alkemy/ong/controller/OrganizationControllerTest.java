package com.alkemy.ong.controller;

import com.alkemy.ong.dto.OrganizationBasicDTO;
import com.alkemy.ong.dto.OrganizationUpdateDTO;
import com.alkemy.ong.dto.SlidesDTO;
import com.alkemy.ong.exception.ResourceNotFoundException;
import com.alkemy.ong.security.service.impl.UserServiceImpl;
import com.alkemy.ong.service.IOrganizationService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static com.alkemy.ong.util.Constants.*;
import static com.alkemy.ong.util.Constants.Endpoints.ORGANIZATION;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class OrganizationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserServiceImpl userService;

    @Mock
    private IOrganizationService organizationService;

    private OrganizationUpdateDTO organizationUpdateDTO;

    private OrganizationBasicDTO organizationBasicDTO;

    private List<SlidesDTO> slidesDTOList;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        organizationUpdateDTO = new OrganizationUpdateDTO("SomosMas",
                12345678, "address",
                "ong.png",
                "welcome",
                "ong",
                "https://www.facebook.com/somosmas",
                "https://www.linkedin.com/profile/somosmas",
                "https://www.instagram.com/somosmas",
                "somosmas@ong.com"
                );

        slidesDTOList = new ArrayList<>();
        slidesDTOList.add(new SlidesDTO(1L, "slide1.jpg", "text1", 1));
        organizationBasicDTO = new OrganizationBasicDTO("SomosMas",
                12345678,
                "address",
                "ong.png",
                slidesDTOList,
                "https://www.facebook.com/somosmas",
                "https://www.linkedin.com/profile/somosmas",
                "https://www.instagram.com/somosmas");
    }

    @Test
    @DisplayName("All users can get public information of the organization")
    @WithMockUser(username = "user", authorities = {ROLE_ADMIN, ROLE_USER})
    void getOrganizationWithAuthorities() throws Exception{
        when(organizationService.getOrganizationBasic())
                .thenReturn(organizationBasicDTO);
        mockMvc.perform(get("/organization/public")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .with(user("user").roles("ADMIN", "USER"))
                        .with(csrf()))
                .andExpect(status().isOk());
    }
    @Test
    @DisplayName("Admin users can update the organization data")
    @WithMockUser(username = "admin", authorities = ROLE_ADMIN)
    void updateOrganizationByAdmin() throws Exception {
        when(organizationService.updateOrganization(1L, organizationUpdateDTO)).
                thenReturn(organizationUpdateDTO);
        mockMvc.perform(patch("/organization/public/" + 1L)
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(organizationUpdateDTO))
                        .with(user("admin").roles("ADMIN"))
                        .with(csrf()))
                .andExpect(status().isAccepted());
    }

    @Test
    @DisplayName("Cannot update a non existing organization")
    @WithMockUser(username = "admin", authorities = ROLE_ADMIN)
    void updateNonExistingOrganizationFailed() throws Exception{
        Long idOrgNotExist = 3L;
        when(organizationService.updateOrganization(idOrgNotExist, organizationUpdateDTO)).
                thenThrow(ResourceNotFoundException.class);
        mockMvc.perform(patch("/organization/public/" + idOrgNotExist)
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(organizationUpdateDTO))
                        .with(user("admin").roles("ADMIN"))
                        .with(csrf()))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Update of the organization data fails because the email format is wrong")
    @WithMockUser(username = "admin", authorities = ROLE_ADMIN)
    void updateOrganizationWrongEmailFormat() throws Exception {
        organizationUpdateDTO.setEmail("email");
        when(organizationService.updateOrganization(1L, organizationUpdateDTO)).
                thenReturn(organizationUpdateDTO);
        mockMvc.perform(patch("/organization/public/" + 1L)
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(organizationUpdateDTO))
                        .with(user("admin").roles("ADMIN"))
                        .with(csrf()))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Update of the organization data fails because the Facebook url format is wrong")
    @WithMockUser(username = "admin", authorities = ROLE_ADMIN)
    void updateOrganizationWrongFacebookUrlFormat() throws Exception {
        organizationUpdateDTO.setUrlFacebook("facebook");
        when(organizationService.updateOrganization(1L, organizationUpdateDTO)).
                thenReturn(organizationUpdateDTO);
        mockMvc.perform(patch("/organization/public/" + 1L)
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(organizationUpdateDTO))
                        .with(user("admin").roles("ADMIN"))
                        .with(csrf()))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Update of the organization data fails because the Instagram url format is wrong")
    @WithMockUser(username = "admin", authorities = ROLE_ADMIN)
    void updateOrganizationWrongInstagramUrlFormat() throws Exception {
        organizationUpdateDTO.setUrlInstagram("instagram");
        when(organizationService.updateOrganization(1L, organizationUpdateDTO)).
                thenReturn(organizationUpdateDTO);
        mockMvc.perform(patch("/organization/public/" + 1L)
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(organizationUpdateDTO))
                        .with(user("admin").roles("ADMIN"))
                        .with(csrf()))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Update of the organization data fails because the Linkedin url format is wrong")
    @WithMockUser(username = "admin", authorities = ROLE_ADMIN)
    void updateOrganizationWrongLinkedinUrlFormat() throws Exception {
        organizationUpdateDTO.setUrlLinkedin("linkedin");
        when(organizationService.updateOrganization(1L, organizationUpdateDTO)).
                thenReturn(organizationUpdateDTO);
        mockMvc.perform(patch("/organization/public/" + 1L)
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(organizationUpdateDTO))
                        .with(user("admin").roles("ADMIN"))
                        .with(csrf()))
                .andExpect(status().isBadRequest());
    }
    @Test
    @DisplayName("Cannot update the organization data with no credentials")
    void updateOrganizationForbidden() throws Exception {
        mockMvc.perform(patch(ORGANIZATION + 1L)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .with(csrf()))
                .andExpect(status().isForbidden());
    }
    @Test
    @DisplayName("Cannot update the organization data with no credentials")
    void getOrganizationForbidden() throws Exception {
        mockMvc.perform(get(ORGANIZATION)
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .with(csrf()))
                .andExpect(status().isForbidden());

    }

}