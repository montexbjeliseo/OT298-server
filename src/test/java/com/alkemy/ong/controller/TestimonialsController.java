package com.alkemy.ong.controller;

import com.alkemy.ong.dto.TestimonialDTO;
import com.alkemy.ong.exception.ResourceNotFoundException;
import com.alkemy.ong.model.Testimonial;
import com.alkemy.ong.repository.TestimonialRepository;
import com.alkemy.ong.security.service.impl.UserServiceImpl;
import com.alkemy.ong.service.impl.TestimonialServiceImpl;
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
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import java.util.*;

import static com.alkemy.ong.util.Constants.ROLE_ADMIN;
import static com.alkemy.ong.util.Constants.ROLE_USER;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@MockitoSettings(strictness = Strictness.LENIENT)
@AutoConfigureMockMvc
public class TestimonialsController {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserServiceImpl service;

    @MockBean
    private TestimonialRepository repositoryBean;

    @MockBean
    private TestimonialServiceImpl testimonialService;

    private ObjectMapper objectMapper = new ObjectMapper();

    private ObjectWriter objectWriter = objectMapper.writer();

    private TestimonialDTO testimonial;

    @Mock
    private Pageable pageable;


    @BeforeEach
    private void init() {
        testimonial = new TestimonialDTO("testimonial name", "testimonial.jpg", "testimonial content");

    }

    // post
    @Test
    @DisplayName("Cannot create a testimonial without authorization")
    public void Testimonials_creation_without_authorization_is_forbidden() throws Exception {
        postingIsForbidden("/testimonials");
    }

    @Test
    @DisplayName("If any argument is invalid the testimonial is not created")
    @WithMockUser(username = "user", authorities = ROLE_ADMIN)
    public void An_invalid_testimonial_is_not_created() throws Exception {
        TestimonialDTO emptyDto = new TestimonialDTO();
        Mockito.when(testimonialService.save(emptyDto)).thenReturn(emptyDto);
        String content = objectWriter.writeValueAsString(emptyDto);
        MockHttpServletRequestBuilder mockRequest = buildPostRequest("/testimonials", content);
        mockMvc.perform(mockRequest)
                .andExpect(status().isBadRequest()).andDo(print());
    }

    @Test
    @DisplayName("An admin user can create a testimonial")
    @WithMockUser(username = "user", authorities = ROLE_ADMIN)
    public void Testimonial_creation_with_authentication_is_successful() throws Exception {
        Mockito.when(testimonialService.save(testimonial)).thenReturn(testimonial);
        String content = objectWriter.writeValueAsString(testimonial);

        MockHttpServletRequestBuilder mockRequest = buildPostRequest("/testimonials", content);

        mockMvc.perform(mockRequest)
                .andExpect(status().isOk()).andDo(print());
    }

    // delete

    @Test
    @DisplayName("Cannot delete a testimonial without authorization")
    public void Testimonials_deletion_without_authorization_is_forbidden() throws Exception {
        deletingIsForbidden("/testimonials", 1L);
    }


    @Test
    @DisplayName("Cannot delete a testimonial without admin authority")
    @WithMockUser(username = "user", authorities = ROLE_USER)
    public void Testimonial_deletion_without_proper_authority_is_successful() throws Exception {
        when(testimonialService.findById(1L)).thenReturn(Optional.of(new Testimonial()));
        deletingIsForbidden("/testimonials", 1L);
    }

    @Test
    @DisplayName("Can delete a testimonial with admin authority")
    @WithMockUser(username = "user", authorities = ROLE_ADMIN)
    public void Testimonial_deletion_with_proper_authority_is_successful() throws Exception {
        when(testimonialService.findById(1L)).thenReturn(Optional.of(new Testimonial()));
        mockMvc.perform(delete("/testimonials" + "/" + 1L))
                .andExpect(status().is2xxSuccessful())
                .andDo(print());
    }


    // update

    @Test
    @DisplayName("Cannot access /testimonials/{id} without credentials")
    public void Testimonial_update_without_authorization_is_forbidden() throws Exception {
        mockMvc.perform(buildPutRequest("/testimonials/1", ""));
    }


    @Test
    @DisplayName("A non existent testimonial cannot be updated")
    @WithMockUser(username = "user", authorities = ROLE_ADMIN)
    public void A_non_existent_activity_cannot_be_updated() throws Exception {
        TestimonialDTO nonExistentTestimonial = new TestimonialDTO();

        Mockito.when(testimonialService.update(1L, nonExistentTestimonial)).thenThrow(ResourceNotFoundException.class);

        String content = objectWriter.writeValueAsString(nonExistentTestimonial);

        MockHttpServletRequestBuilder mockRequest = buildPutRequest("/testimonials/1", content);

        mockMvc.perform(mockRequest)
                .andExpect(status().isBadRequest()).andDo(print());
    }


    @Test
    @DisplayName("An existing testimonial can be updated")
    @WithMockUser(username = "user", authorities = ROLE_ADMIN)
    public void An_existing_activity_can_be_updated() throws Exception {
        Mockito.when(testimonialService.update(1L, testimonial)).thenReturn(testimonial);

        String content = objectWriter.writeValueAsString(testimonial);

        MockHttpServletRequestBuilder mockRequest = buildPutRequest("/testimonials/1", content);


        mockMvc.perform(mockRequest)
                .andExpect(status().isOk()).andDo(print());
    }

    // get


    @Test
    @DisplayName("Testimonials can be reached and returns a page of testimonials")
    @WithMockUser(username = "ALL_ROLES", authorities = {ROLE_USER, ROLE_ADMIN})
    public void Testimonials_pagination_returns_a_page() throws Exception {
        Map<String, Object> response = testimonialsPageResponse(1, 20, 4);
        when(testimonialService.responseTestimonialPage(1, pageable)).thenReturn(response);

        mockMvc.perform(get("/testimonials/page")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());

    }


    // auxiliary methods
    private void postingIsForbidden(String endpoint) throws Exception {
        mockMvc.perform(post(endpoint))
                .andExpect(status().isForbidden()).andDo(print());
    }

    private void deletingIsForbidden(String endpoint, Long id) throws Exception {

        mockMvc.perform(delete(endpoint + "/" + id))
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

    private Map<String, Object> testimonialsPageResponse(int pageNumber, int totalElements, int totalPages) {
        Map<String, Object> response = new HashMap<>();
        response.put("testimonials", Arrays.asList(Mockito.mock(Testimonial.class), Mockito.mock(Testimonial.class)));
        response.put("currentPage", pageNumber);
        response.put("totalElements", totalElements);
        response.put("totalPages", totalPages);

        return response;
    }


}