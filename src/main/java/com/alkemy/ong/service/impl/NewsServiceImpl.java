package com.alkemy.ong.service.impl;

import com.alkemy.ong.dto.NewsPaginationDto;
import com.alkemy.ong.exception.InvalidPaginationParamsException;
import com.alkemy.ong.exception.ResourceNotFoundException;
import com.alkemy.ong.exception.ThereAreNoCategories;
import com.alkemy.ong.model.Category;
import com.alkemy.ong.repository.CategoryRepository;
import com.alkemy.ong.util.Pagination;
import com.alkemy.ong.dto.CommentBasicDTO;
import com.alkemy.ong.exception.ThereAreNoCommentsByNew;
import com.alkemy.ong.mapper.CommentMapper;
import com.alkemy.ong.model.Comment;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;

import com.alkemy.ong.repository.NewsRepository;
import com.alkemy.ong.service.INewsService;
import com.alkemy.ong.mapper.NewsMapper;
import com.alkemy.ong.model.News;
import com.alkemy.ong.dto.NewsDto;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.*;

@Service
@Transactional
public class NewsServiceImpl implements INewsService {
    @Autowired
    private NewsRepository repo;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private NewsMapper mapper;

    @Autowired
    private CommentMapper mapperComment;

    @Autowired
    private MessageSource message;

    @Override
    public List<CommentBasicDTO> getAllCommentsByNewsId(Long id) {
        if(!findById(id).isPresent()){
            throw new ResourceNotFoundException(message.getMessage("news.notFound", null, Locale.US));
        }

        List<Comment> comments = repo.findCommentsByNewsId(id);
        if (comments.isEmpty()) {
            throw new ThereAreNoCommentsByNew(message.getMessage("new.commentsThereAreNo", null, Locale.US));
        }

        List<CommentBasicDTO> commentsDtos = mapperComment.listCommentsToListDtos(comments);
        return commentsDtos;
    }

    public NewsDto save(NewsDto dto) {
        Optional<Category> category = categoryRepository.findById(dto.getCategoryId());
        if(!category.isPresent()){
            throw new ThereAreNoCategories(message.getMessage("category.notFound", null, Locale.US));
        }
        News news = repo.save(mapper.toEntity(dto));
        return mapper.toDto(news);
    }

    @Override
    public NewsDto update(Long id, NewsDto dto) {
        if (!findById(id).isPresent()) {
            throw new ResourceNotFoundException(message.getMessage("news.notFound", null, Locale.US));
        }
        Optional<Category> category = categoryRepository.findById(dto.getCategoryId());
        if(!category.isPresent()){
            throw new ThereAreNoCategories(message.getMessage("category.notFound", null, Locale.US));
        }
        News newsEntity = repo.findById(id).get();
        News news = repo.save(mapper.updateNewsFromDto(dto, newsEntity));
        NewsDto newsDto = mapper.toDto(news);
        return newsDto;
    }

    @Override
    public void deleteById(Long id) {
        if (!findById(id).isPresent()) {
            throw new ResourceNotFoundException(message.getMessage("news.notFound", null, Locale.US));
        }
        repo.deleteById(id);
    }

    public boolean existsById(Long id) {
        return repo.existsById(id);
    }

    public Optional<News> findById(Long id) {
        return repo.findById(id);
    }

    public Optional<List> findAll() {
        return Optional.of(repo.findAll());
    }

    public NewsDto getById(Long id) {
        if (findById(id).isPresent()) {
            News news = findById(id).get();
            return mapper.toDto(news);
        } else {
            throw new ResourceNotFoundException(message.getMessage("news.notFound", null, Locale.US));
        }
    }

    @Override
    public NewsPaginationDto getPaginated(Pageable pageable, HttpServletRequest request, UriComponentsBuilder uriBuilder) {
        int pageNumber = pageable.getPageNumber();

        int pageSize = pageable.getPageSize();

        Page<News> resultPage = repo.findAll(pageable);
        List<News> news = resultPage.getContent();
        List<NewsDto> newsDtos = mapper.listNewsToNewsDto(news);

        int totalPages = resultPage.getTotalPages();

        if (pageNumber < 0 || pageNumber >= totalPages) {
            throw new InvalidPaginationParamsException(message.getMessage("pagination.invalidArgs", null, Locale.US));
        }

        uriBuilder.path(request.getRequestURI());

        Map<String, String> links = new LinkedHashMap<>();

        if (resultPage.hasPrevious()) {
            links.put(message.getMessage("pagination.previous", null, Locale.US),
                    Pagination.constructPreviousPageUri(uriBuilder, pageNumber, pageSize));
        }
        if (resultPage.hasNext()) {
            links.put(message.getMessage("pagination.next", null, Locale.US),
                    Pagination.constructNextPageUri(uriBuilder, pageNumber, pageSize));
        }

        return new NewsPaginationDto(newsDtos, links);
    }
}