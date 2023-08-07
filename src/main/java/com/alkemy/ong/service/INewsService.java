package com.alkemy.ong.service;

import com.alkemy.ong.dto.CommentBasicDTO;
import com.alkemy.ong.dto.NewsDto;
import com.alkemy.ong.dto.NewsPaginationDto;
import com.alkemy.ong.model.News;
import org.springframework.data.domain.Pageable;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;
import java.util.List;

public interface INewsService {
    NewsDto save(NewsDto dto);

    NewsDto update(Long id, NewsDto dto);

    void deleteById(Long id);

    boolean existsById(Long id);

    Optional<News> findById(Long id);

    Optional<List> findAll();

    NewsDto getById(Long id);

    NewsPaginationDto getPaginated(Pageable pageable, HttpServletRequest request, UriComponentsBuilder uriBuilder);

    List<CommentBasicDTO> getAllCommentsByNewsId(Long id);
}
