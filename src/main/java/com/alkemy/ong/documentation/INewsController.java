package com.alkemy.ong.documentation;

import com.alkemy.ong.dto.CommentBasicDTO;
import com.alkemy.ong.dto.NewsDto;
import com.alkemy.ong.dto.NewsPaginationDto;
import com.alkemy.ong.util.Pagination;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

@Tag(name = "News", description = "${news.docs.tagDescription}")
public interface INewsController {


    @Operation(summary = "${news.docs.save.operation.summary}", description = "${news.docs.save.operation.description}")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "${news.docs.save.response.ok.description}", content = @Content),
            @ApiResponse(responseCode = "403", description = "${news.docs.save.response.accessDenied.description}", content = @Content),
            @ApiResponse(responseCode = "400", description = "${news.docs.save.response.badRequest.description}", content = @Content)

    })
    ResponseEntity<?> saveNews(@Valid @RequestBody NewsDto newsDto) throws Exception;

    @Operation(summary = "${news.docs.getById.operation.summary}", description = "${news.docs.getById.operation.description}")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "${news.docs.getById.response.ok.description}", content = @Content),
            @ApiResponse(responseCode = "403", description = "${news.docs.getById.response.accessDenied.description}", content = @Content),
            @ApiResponse(responseCode = "404", description = "${news.docs.getById.response.notFound.description}", content = @Content)
    })
    ResponseEntity<NewsDto> getById(@PathVariable Long id);


    @Operation(summary = "${news.docs.getComments.operation.summary}", description = "${news.docs.getComments.operation.description}")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "${news.docs.getComments.response.ok.description}", content = @Content),
            @ApiResponse(responseCode = "204", description = "${news.docs.getComments.response.noContent.description}", content = @Content),
            @ApiResponse(responseCode = "403", description = "${news.docs.getComments.response.accessDenied.description}", content = @Content)
    })
    ResponseEntity<List<CommentBasicDTO>> getCommentsByNew(@PathVariable Long id);

    @Operation(summary = "${news.docs.update.operation.summary}", description = "${news.docs.update.operation.description}")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "${news.docs.update.response.ok.description}", content = @Content),
            @ApiResponse(responseCode = "403", description = "${news.docs.update.response.accessDenied.description}", content = @Content),
            @ApiResponse(responseCode = "400", description = "${news.docs.update.response.badRequest.description}", content = @Content),
            @ApiResponse(responseCode = "404", description = "${news.docs.update.response.notFound.description}", content = @Content)

    })
    ResponseEntity<NewsDto> updateNews(@PathVariable Long id, @Valid @RequestBody NewsDto newsDto);

    @Operation(summary = "${news.docs.delete.operation.summary}", description = "${news.docs.delete.operation.description}")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "${news.docs.delete.response.ok.description}", content = @Content),
            @ApiResponse(responseCode = "403", description = "${news.docs.delete.response.accessDenied.description}", content = @Content),
            @ApiResponse(responseCode = "404", description = "${news.docs.delete.response.notFound.description}", content = @Content),
            @ApiResponse(responseCode = "500", description = "${news.docs.delete.response.genericError.description}", content = @Content)
    })
    ResponseEntity<Void> deleteNews(@PathVariable Long id);

    @Operation(summary = "${news.docs.getPaginated.operation.summary}", description = "${news.docs.getPaginated.operation.description}")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "${news.docs.getPaginated.response.ok.description}", content = @Content),
            @ApiResponse(responseCode = "400", description = "${news.docs.getPaginated.response.badRequest.description}", content = @Content)
    })
    ResponseEntity<NewsPaginationDto> getPaginated(
            @Valid @PageableDefault(page = Pagination.INITIAL_PAGE, size = Pagination.PAGE_SIZE) Pageable pageable,
            HttpServletRequest request,
            UriComponentsBuilder uriBuilder);

}