package com.alkemy.ong.controller;

import com.alkemy.ong.documentation.INewsController;
import com.alkemy.ong.dto.CommentBasicDTO;
import com.alkemy.ong.dto.NewsDto;
import com.alkemy.ong.dto.NewsPaginationDto;
import com.alkemy.ong.service.INewsService;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import com.alkemy.ong.util.Pagination;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@RestController
@RequestMapping("/news")@PropertySource("classpath:documentation/newsController.properties")
public class NewsController implements INewsController {

    @Autowired
    private INewsService newsService;

    @PostMapping
    public ResponseEntity<?> saveNews(@Valid @RequestBody NewsDto newsDto) throws Exception {
        return new ResponseEntity<>(newsService.save(newsDto), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<NewsDto> getById(@PathVariable Long id) {
        NewsDto newsDto = newsService.getById(id);
        return ResponseEntity.ok().body(newsDto);
    }

    @GetMapping("/{id}/comments")
    public ResponseEntity<List<CommentBasicDTO>> getCommentsByNew(@PathVariable Long id) {
        List<CommentBasicDTO> comments = newsService.getAllCommentsByNewsId(id);
        return new ResponseEntity<List<CommentBasicDTO>>(comments, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<NewsDto> updateNews(@PathVariable Long id, @Valid @RequestBody NewsDto newsDto) {
        NewsDto news = newsService.update(id, newsDto);
        return new ResponseEntity<NewsDto>(news, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteNews(@PathVariable Long id) {
        newsService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }


    @GetMapping()
    public ResponseEntity<NewsPaginationDto> getPaginated(
            @Valid @PageableDefault(page = Pagination.INITIAL_PAGE, size = Pagination.PAGE_SIZE) Pageable pageable,
            HttpServletRequest request,
            UriComponentsBuilder uriBuilder) {
        return ResponseEntity.ok(newsService.getPaginated(pageable, request, uriBuilder));
    }


}
