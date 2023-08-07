package com.alkemy.ong;

import com.alkemy.ong.dto.CommentBasicDTO;
import com.alkemy.ong.dto.NewsDto;
import com.alkemy.ong.dto.NewsPaginationDto;
import com.alkemy.ong.exception.InvalidPaginationParamsException;
import com.alkemy.ong.exception.ResourceNotFoundException;
import com.alkemy.ong.exception.ThereAreNoCommentsByNew;
import com.alkemy.ong.security.service.impl.UserServiceImpl;
import com.alkemy.ong.service.INewsService;
import com.alkemy.ong.util.Pagination;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static com.alkemy.ong.util.Constants.Endpoints.*;
import static com.alkemy.ong.util.Constants.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class NewsControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private INewsService newsService;

    @MockBean
    private UserServiceImpl userService;

    private ObjectMapper objectMapper = new ObjectMapper();

    private NewsDto correctDto;
    private NewsDto incorrectDto;
    private List<NewsDto> listNews;
    private List<CommentBasicDTO> listCommentsByNews;
    private NewsPaginationDto paginationDto;

    @BeforeEach
    public void setUp(){
        correctDto = returnCorrectNewsDto();
        incorrectDto = returnIncorrectNewsDto();
        listNews = returnListNews();
        listCommentsByNews = returnListCommentsByNews();
        paginationDto = returnNewsPaginationDto();
    }

    @AfterEach
    public void tearDown(){
        correctDto = null;
        incorrectDto = null;
        listNews = null;
        listCommentsByNews = null;
        paginationDto = null;
    }


    @Test
    @WithMockUser(username = "ADMIN", authorities = ROLE_ADMIN)
    public void Add_news_with_authorization_is_possible() throws Exception{

        when(newsService.save(any(NewsDto.class))).thenReturn(correctDto);

        ResultActions result = mockMvc.perform(post(NEWS)
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(correctDto))
                .contentType(MediaType.APPLICATION_JSON));

        result.andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(correctDto.getName()))
                .andExpect(jsonPath("$.content").value(correctDto.getContent()))
                .andExpect(jsonPath("$.image").value(correctDto.getImage()))
                .andExpect(jsonPath("$.categoryId").value(correctDto.getCategoryId()))
                .andDo(print());

    }

    @Test
    @WithMockUser(username = "ADMIN", authorities = ROLE_ADMIN)
    public void Add_news_without_valid_date_is_not_possible() throws Exception{

        ResultActions result = mockMvc.perform(post(NEWS)
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(incorrectDto))
                .contentType(MediaType.APPLICATION_JSON));

        result.andExpect(status().isBadRequest())
                .andDo(print());

    }

    @Test
    @WithMockUser(username = "USER", authorities = ROLE_USER)
    public void Add_news_without_authorization_is_forbidden() throws Exception{

        ResultActions result = mockMvc.perform(post(NEWS)
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(correctDto))
                .contentType(MediaType.APPLICATION_JSON));

        result.andExpect(status().isForbidden())
                .andDo(print());

    }


    @Test
    @WithMockUser(username = "ADMIN", authorities = ROLE_ADMIN)
    public void Update_news_with_authorization_is_possible() throws Exception{

        when(newsService.update(anyLong(), any(NewsDto.class))).thenReturn(correctDto);

        ResultActions result = mockMvc.perform(put(NEWS.concat("/1"))
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(correctDto))
                .contentType(MediaType.APPLICATION_JSON));

        result.andExpect((status().isOk()))
                .andExpect(jsonPath("$.name").value(correctDto.getName()))
                .andExpect(jsonPath("$.content").value(correctDto.getContent()))
                .andExpect(jsonPath("$.image").value(correctDto.getImage()))
                .andExpect(jsonPath("$.categoryId").value(correctDto.getCategoryId()))
                .andDo(print());

    }

    @Test
    @WithMockUser(username = "ADMIN", authorities = ROLE_ADMIN)
    public void Update_news_without_valid_data_is_not_possible() throws Exception{

        ResultActions result = mockMvc.perform(put(NEWS.concat("/1"))
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(incorrectDto))
                .contentType(MediaType.APPLICATION_JSON));

        result.andExpect(status().isBadRequest())
                .andDo(print());

    }

    @Test
    @WithMockUser(username = "ADMIN", authorities = ROLE_ADMIN)
    public void Update_news_without_found_is_not_possible() throws Exception{

        when(newsService.update(anyLong(), any(NewsDto.class))).thenThrow(ResourceNotFoundException.class);

        ResultActions result = mockMvc.perform(put(NEWS.concat("/1"))
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(correctDto))
                .contentType(MediaType.APPLICATION_JSON));

        result.andExpect(status().isNotFound())
                .andExpect(jsonPath("$.name").doesNotExist())
                .andExpect(jsonPath("$.content").doesNotExist())
                .andExpect(jsonPath("$.image").doesNotExist())
                .andExpect(jsonPath("$.categoryId").doesNotExist())
                .andDo(print());

    }

    @Test
    @WithMockUser(username = "USER", authorities = ROLE_USER)
    public void Update_news_without_authorization_is_forbidden() throws Exception{

        ResultActions result = mockMvc.perform(put(NEWS.concat("/1"))
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(correctDto))
                .contentType(MediaType.APPLICATION_JSON));

        result.andExpect(status().isForbidden())
                .andDo(print());

    }


    @Test
    @WithMockUser(username = "ADMIN", authorities = ROLE_ADMIN)
    public void Delete_news_with_authorization_is_possible() throws Exception{

        ResultActions result = mockMvc.perform(delete(NEWS.concat("/1")));

        result.andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @WithMockUser(username = "ADMIN", authorities = ROLE_ADMIN)
    public void Delete_news_without_found_is_not_possible() throws Exception{

        doThrow(ResourceNotFoundException.class).when(newsService).deleteById(anyLong());

        ResultActions result = mockMvc.perform(delete(NEWS.concat("/1"))
                .accept(MediaType.APPLICATION_JSON));

        result.andExpect(status().isNotFound())
                .andDo(print());

    }

    @Test
    @WithMockUser(username = "USER", authorities = ROLE_USER)
    public void Delete_news_without_authorization_is_forbidden() throws Exception{

        ResultActions result = mockMvc.perform(delete(NEWS.concat("/1")));

        result.andExpect(status().isForbidden())
                .andDo(print());

    }


    @Test
    @WithMockUser(username = "ADMIN", authorities = ROLE_ADMIN)
    public void Get_news_by_id_with_authorization_is_possible() throws Exception{
        when(newsService.getById(anyLong())).thenReturn(correctDto);

        ResultActions result = mockMvc.perform(get(NEWS.concat("/1"))
                .accept(MediaType.APPLICATION_JSON));

        result.andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(correctDto.getName()))
                .andExpect(jsonPath("$.content").value(correctDto.getContent()))
                .andExpect(jsonPath("$.image").value(correctDto.getImage()))
                .andExpect(jsonPath("$.categoryId").value(correctDto.getCategoryId()))
                .andDo(print());

    }

    @Test
    @WithMockUser(username = "ADMIN", authorities = ROLE_ADMIN)
    public void Get_news_by_id_without_found_is_not_possible() throws Exception{
        when(newsService.getById(anyLong())).thenThrow(ResourceNotFoundException.class);

        ResultActions result = mockMvc.perform(get(NEWS.concat("/1"))
                .accept(MediaType.APPLICATION_JSON));

        result.andExpect(status().isNotFound())
                .andExpect(jsonPath("$.name").doesNotExist())
                .andExpect(jsonPath("$.content").doesNotExist())
                .andExpect(jsonPath("$.image").doesNotExist())
                .andExpect(jsonPath("$.categoryId").doesNotExist())
                .andDo(print());
    }

    @Test
    @WithMockUser(username = "USER", authorities = ROLE_USER)
    public void Get_news_by_id_without_authorization_is_forbidden() throws Exception{

        ResultActions result = mockMvc.perform(get(NEWS.concat("/1"))
                .accept(MediaType.APPLICATION_JSON));

        result.andExpect(status().isForbidden())
                .andDo(print());
    }


    @Test
    @WithMockUser(username = "ALL_ROLES", authorities = {ROLE_USER, ROLE_ADMIN})
    public void Get_comments_by_news_is_possible() throws Exception{

        when(newsService.getAllCommentsByNewsId(anyLong())).thenReturn(listCommentsByNews);

        ResultActions result = mockMvc.perform(get(NEWS.concat("/1/comments"))
                .accept(MediaType.APPLICATION_JSON));
        result.andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(listCommentsByNews.size()))
                .andExpect(jsonPath("$[?(@.body === 'Body of the comment 1')]").exists())
                .andExpect(jsonPath("$[?(@.body === 'Body of the comment 2')]").exists())
                .andExpect(jsonPath("$[?(@.body === 'Body of the comment 3')]").exists())
                .andDo(print());

    }

    @Test
    @WithMockUser(username = "ALL_ROLES", authorities = {ROLE_USER, ROLE_ADMIN})
    public void Get_comments_by_news_without_found_news_is_not_possible() throws Exception{

        when(newsService.getAllCommentsByNewsId(anyLong())).thenThrow(ResourceNotFoundException.class);

        ResultActions result = mockMvc.perform(get(NEWS.concat("/1/comments"))
                .accept(MediaType.APPLICATION_JSON));

        result.andExpect(status().isNotFound())
                .andExpect(jsonPath("$[?(@.body === 'Body of the comment 1')]").doesNotExist())
                .andExpect(jsonPath("$[?(@.body === 'Body of the comment 2')]").doesNotExist())
                .andExpect(jsonPath("$[?(@.body === 'Body of the comment 3')]").doesNotExist())
                .andDo(print());

    }

    @Test
    @WithMockUser(username = "ALL_ROLES", authorities = {ROLE_USER, ROLE_ADMIN})
    public void Get_comments_by_news_without_found_comments_is_not_possible() throws Exception{

        when(newsService.getAllCommentsByNewsId(anyLong())).thenThrow(ThereAreNoCommentsByNew.class);

        ResultActions result = mockMvc.perform(get(NEWS.concat("/1/comments"))
                .accept(MediaType.APPLICATION_JSON));

        result.andExpect(status().isNoContent())
                .andDo(print());

    }


    @Test
    @WithMockUser(username = "ALL_ROLES", authorities = {ROLE_USER, ROLE_ADMIN})
    public void Get_page_news_is_possible() throws Exception{

        when(newsService.getPaginated(any(), any(), any())).thenReturn(paginationDto);

        ResultActions result = mockMvc.perform(get(NEWS)
                .accept(MediaType.APPLICATION_JSON));

        result.andExpect(status().isOk())
                .andDo(print());

    }

    @Test
    @WithMockUser(username = "ALL_ROLES", authorities = {ROLE_USER, ROLE_ADMIN})
    public void Get_page_news_without_valid_date_is_not_possible() throws Exception{

        when(newsService.getPaginated(any(), any(), any())).thenThrow(InvalidPaginationParamsException.class);

        ResultActions result = mockMvc.perform(get(NEWS)
                .accept(MediaType.APPLICATION_JSON));

        result.andExpect(status().isBadRequest())
                .andDo(print());

    }


    public static NewsDto returnCorrectNewsDto(){
        NewsDto dto = new NewsDto("News1", "Content1", "example.png", 1L);
        return dto;
    }

    public static NewsDto returnIncorrectNewsDto(){
        NewsDto dto = new NewsDto();
        dto.setName("News1");
        dto.setImage("example.png");
        return dto;
    }

    public static NewsPaginationDto returnNewsPaginationDto(){
        LocalDateTime dateTime = LocalDateTime.now();
        NewsDto news1 = new NewsDto("News1", "Content1", "example.png", 1L);
        NewsDto news2 = new NewsDto("News2", "Content2", "example.png", 1L);
        List<NewsDto> listNews = new ArrayList<>();
        listNews.add(news1);
        listNews.add(news2);

        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromUri(URI.create("/news"));
        Integer pageNumber = 0;
        Integer pageSize = 10;
        Map<String, String> links = new LinkedHashMap<>();
        links.put("nextPage", Pagination.constructNextPageUri(uriBuilder, pageNumber, pageSize));

        NewsPaginationDto paginationDto = new NewsPaginationDto(listNews, links);

        return paginationDto;
    }

    public static List<NewsDto> returnListNews(){
        NewsDto dto1 = new NewsDto("News1", "Content1", "example.png", 1L);
        NewsDto dto2 = new NewsDto("News2", "Content2", "example.png", 1L);
        NewsDto dto3 = new NewsDto("News3", "Content3", "example.png", 1L);

        List<NewsDto> listNews = new ArrayList<>();
        listNews.add(dto1);
        listNews.add(dto2);
        listNews.add(dto3);

        return listNews;
    }

    public static List<CommentBasicDTO> returnListCommentsByNews(){
        CommentBasicDTO dto1 = new CommentBasicDTO("Body of the comment 1");
        CommentBasicDTO dto2 = new CommentBasicDTO("Body of the comment 2");
        CommentBasicDTO dto3 = new CommentBasicDTO("Body of the comment 3");

        List<CommentBasicDTO> listComments = new ArrayList<>();
        listComments.add(dto1);
        listComments.add(dto2);
        listComments.add(dto3);

        return listComments;
    }
}
