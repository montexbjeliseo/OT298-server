package com.alkemy.ong.controller;

import com.alkemy.ong.dto.CategoryBasicDTO;
import com.alkemy.ong.dto.CategoryCompleteGetDto;
import com.alkemy.ong.dto.CategoryDTO;
import com.alkemy.ong.exception.ResourceNotFoundException;
import com.alkemy.ong.service.ICategoryService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static com.alkemy.ong.util.Constants.ROLE_ADMIN;
import static com.alkemy.ong.util.Constants.ROLE_USER;
import static org.hibernate.internal.log.DeprecationLogger.CATEGORY;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(CategoryController.class)
public class CategoryControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ICategoryService categoryService;

    private ObjectMapper objectMapper = new ObjectMapper();

    private CategoryCompleteGetDto categoryCompleteGetDto;

    private CategoryCompleteGetDto notAuthCategoryDto;

    private CategoryDTO saveCategoryDTO;

    private CategoryBasicDTO updateCategoryDTO;



    @BeforeEach
    void setUp(){
        categoryCompleteGetDto = getDto();
        notAuthCategoryDto = notAuthCategoryDto();
        saveCategoryDTO = saveCategoryDto();
        updateCategoryDTO = updateCategoryDto();
    }


    @AfterEach
    public void tearDown(){
        categoryCompleteGetDto = null;
        saveCategoryDTO = null;
        updateCategoryDTO = null;
    }


    @Test
    @DisplayName("get categoryId Auth")
    @WithMockUser(username = "ADMIN", authorities = ROLE_ADMIN)
    void getCategoryIdAuth() throws Exception {
        when(categoryService.getCategoryById(anyLong())).thenReturn(categoryCompleteGetDto);
        mockMvc.perform(get("/categories/4")
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(categoryCompleteGetDto)))
                .andExpect(jsonPath("$.name").value("name"))
                .andExpect(jsonPath("$.image").value("url/img"))
                .andExpect(jsonPath("$.description").value("description"))
                .andExpect(status().isOk())
                .andDo(print());
    }


    @Test
    @DisplayName("get categoryId Not Auth")
    @WithMockUser(username = "ADMIN", authorities = ROLE_ADMIN)
    void getCategoryIdNotAuth() throws Exception {
        when(categoryService.getCategoryById(anyLong())).thenThrow(ResourceNotFoundException.class);
        mockMvc.perform(get(CATEGORY.concat("/4"))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.name").doesNotExist())
                .andExpect(jsonPath("$.image").doesNotExist())
                .andExpect(jsonPath("$.description").doesNotExist())
                .andDo(print());
    }



    @Test
    @DisplayName("save category Auth")
    @WithMockUser(username = "ADMIN", authorities = ROLE_ADMIN)
    void saveCategory() throws Exception {
        when(categoryService.save(any(CategoryDTO.class))).thenReturn(saveCategoryDTO);
        mockMvc.perform(post("/categories")
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(saveCategoryDTO))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.name").value("save"))
                .andExpect(jsonPath("$.image").value("url/save"))
                .andExpect(jsonPath("$.description").value("save"))
                .andExpect(status().isCreated())
                .andDo(print());
    }


    @Test
    @DisplayName("save category Not Auth")
    @WithMockUser(username = "USER", authorities = ROLE_USER)
    void saveCategoryNotAuth() throws Exception {
        mockMvc.perform(post(CATEGORY)
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(notAuthCategoryDto))
                .contentType(MediaType.APPLICATION_JSON))
                 .andExpect(status().isBadRequest())
                .andDo(print());
    }


    @Test
    @DisplayName("update category Auth")
    @WithMockUser(username = "ADMIN", authorities = ROLE_ADMIN)
    public void updateCategoryAuth() throws Exception{
        when(categoryService.update(1L,updateCategoryDTO)).thenReturn(saveCategoryDTO);
        mockMvc.perform(put(CATEGORY.concat("/1"))
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateCategoryDTO))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());
    }


    @Test
    @DisplayName("update category Not Auth")
    @WithMockUser(username = "ADMIN", authorities = ROLE_ADMIN)
    public void updateCatNotAuth() throws Exception{
        mockMvc.perform(put(CATEGORY.concat("/1"))
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(notAuthCategoryDto))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andDo(print());
    }


    public static CategoryCompleteGetDto getDto(){
        CategoryCompleteGetDto categoryDTO = new CategoryCompleteGetDto();
        categoryDTO.setName("name");
        categoryDTO.setImage("url/img");
        categoryDTO.setDescription("description");
        return  categoryDTO;
    }

    public static CategoryDTO saveCategoryDto(){
        CategoryDTO categoryDTO = new CategoryDTO();
        categoryDTO.setName("save");
        categoryDTO.setImage("url/save");
        categoryDTO.setDescription("save");
        return  categoryDTO;
    }

    public static CategoryBasicDTO updateCategoryDto(){
        CategoryBasicDTO categoryDTO = new CategoryBasicDTO();
        categoryDTO.setName("update");
        categoryDTO.setImage("url/update");
        categoryDTO.setDescription("update");
        return  categoryDTO;
    }

    public static CategoryCompleteGetDto notAuthCategoryDto(){
        CategoryCompleteGetDto categoryDTO = new CategoryCompleteGetDto();
        categoryDTO.setName("notauth");
        categoryDTO.setImage("url/notauth");
        return  categoryDTO;
    }


}
