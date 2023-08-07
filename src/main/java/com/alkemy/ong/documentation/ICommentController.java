package com.alkemy.ong.documentation;

import com.alkemy.ong.dto.CommentBasicDTO;
import com.alkemy.ong.dto.CommentGetDto;
import com.alkemy.ong.dto.CommentPostDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;

import java.util.List;

import static com.alkemy.ong.util.Constants.httpCodes.*;
import static com.alkemy.ong.util.Constants.messagesForDocs.*;
import static com.alkemy.ong.util.Constants.messagesForDocs.FORBIDDEN;

@Tag(name = "Comment", description = "View, create, delete and update comments")
public interface ICommentController {

    @Operation(summary = CREATE_COMMENT)
    @ApiResponses(value = {
            @ApiResponse(responseCode = STATUS_CREATED, description = COMMENT_CREATED, content = @Content),
            @ApiResponse(responseCode = STATUS_BAD_REQUEST, description = BAD_REQUEST, content = @Content),
            @ApiResponse(responseCode = STATUS_NOT_FOUND, description = NEWS_NOT_FOUND, content = @Content),
            @ApiResponse(responseCode = STATUS_FORBIDDEN, description = FORBIDDEN, content = @Content)
    })
    ResponseEntity<CommentBasicDTO> newComment(@Valid @RequestBody CommentPostDto commentDto, Authentication authentication);

    @Operation(summary = GET_COMMENTS)
    @ApiResponses(value = {
            @ApiResponse(responseCode = STATUS_ACCEPTED, description = GET_COMMENTS_SUCCESSFUL, content = @Content),
            @ApiResponse(responseCode = STATUS_NOT_FOUND, description = NO_COMMENTS, content = @Content),
            @ApiResponse(responseCode = STATUS_FORBIDDEN, description = FORBIDDEN, content = @Content)
    })
    ResponseEntity<List<CommentBasicDTO>> getAllComments ();

    @Operation(summary = DELETE_COMMENT)
    @ApiResponses(value = {
            @ApiResponse(responseCode = STATUS_NO_CONTENT, description = COMMENT_DELETED, content = @Content),
            @ApiResponse(responseCode = STATUS_NOT_FOUND, description = COMMENT_NOT_FOUND, content = @Content),
            @ApiResponse(responseCode = STATUS_FORBIDDEN, description = FORBIDDEN, content = @Content),
            @ApiResponse(responseCode = STATUS_UNAUTHORIZED, description = DELETE_COMMENT_UNAUTHORIZED)
    })
    ResponseEntity<Void> delete(@Valid @PathVariable Long id, Authentication authentication);

    @Operation(summary = UPDATE_COMMENT)
    @ApiResponses(value = {
            @ApiResponse(responseCode = STATUS_ACCEPTED, description = COMMENT_UPDATED, content = @Content),
            @ApiResponse(responseCode = STATUS_NOT_FOUND, description = COMMENT_NOT_FOUND, content = @Content),
            @ApiResponse(responseCode = STATUS_BAD_REQUEST, description = BAD_REQUEST, content = @Content),
            @ApiResponse(responseCode = STATUS_FORBIDDEN, description = FORBIDDEN, content = @Content),
            @ApiResponse(responseCode = STATUS_UNAUTHORIZED, description = UPDATE_COMMENT_UNAUTHORIZED, content = @Content)
    })
    ResponseEntity<CommentGetDto> updateComment (@PathVariable Long id, @RequestBody CommentBasicDTO dto);

}
