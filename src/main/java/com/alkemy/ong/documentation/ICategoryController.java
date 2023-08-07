package com.alkemy.ong.documentation;

import com.alkemy.ong.dto.CategoryBasicDTO;
import com.alkemy.ong.dto.CategoryCompleteGetDto;
import com.alkemy.ong.dto.CategoryDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Map;

import static com.alkemy.ong.util.Constants.FIRST_PAGE;
import static com.alkemy.ong.util.Constants.httpCodes.*;
import static com.alkemy.ong.util.Constants.messagesForDocs.*;

@Tag(name = "Category", description = "View, add, update and delete categories")
public interface ICategoryController {

    @Operation(summary = GET_CATEGORIES)
    @ApiResponses(value = {
            @ApiResponse(responseCode = STATUS_OK, description = GET_CATEGORIES_SUCCESSFUL, content = @Content),
            @ApiResponse(responseCode = STATUS_BAD_REQUEST, description = INVALID_PAGE, content = @Content),
            @ApiResponse(responseCode = STATUS_NOT_FOUND, description = PAGE_NOT_FOUND, content = @Content),
            @ApiResponse(responseCode = STATUS_NOT_FOUND, description = NO_CATEGORIES, content = @Content),
            @ApiResponse(responseCode = STATUS_FORBIDDEN, description = FORBIDDEN, content = @Content)
    })
    ResponseEntity<Map<String, Object>> getAllCategories(
            @RequestParam(defaultValue = FIRST_PAGE, required = false) Integer page);

    @Operation(summary = GET_CATEGORY_ID)
    @ApiResponses(value = {
            @ApiResponse(responseCode = STATUS_OK, description = GET_CATEGORY_SUCCESSFUL, content = @Content),
            @ApiResponse(responseCode = STATUS_NOT_FOUND, description = CATEGORY_NOT_FOUND, content = @Content),
            @ApiResponse(responseCode = STATUS_FORBIDDEN, description = FORBIDDEN, content = @Content)
    })
    ResponseEntity<CategoryCompleteGetDto> getCategoryById(@PathVariable Long id);


    @Operation(summary = ADD_CATEGORY)
    @ApiResponses(value = {
            @ApiResponse(responseCode = STATUS_CREATED, description = CATEGORY_CREATED, content = @Content),
            @ApiResponse(responseCode = STATUS_BAD_REQUEST, description = BAD_REQUEST, content = @Content),
            @ApiResponse(responseCode = STATUS_FORBIDDEN, description = FORBIDDEN, content = @Content)
    })
    ResponseEntity<CategoryDTO> saveCategory (@Valid @RequestBody CategoryDTO dto);

    @Operation(summary = DELETE_CATEGORY)
    @ApiResponses(value = {
            @ApiResponse(responseCode = STATUS_NO_CONTENT, description = CATEGORY_DELETED, content = @Content),
            @ApiResponse(responseCode = STATUS_NOT_FOUND, description = CATEGORY_NOT_FOUND, content = @Content),
            @ApiResponse(responseCode = STATUS_FORBIDDEN, description = FORBIDDEN, content = @Content)
    })
    ResponseEntity<Void> deleteCategory(@PathVariable Long id);

    @Operation(summary = UPDATE_CATEGORY)
    @ApiResponses(value = {
            @ApiResponse(responseCode = STATUS_OK, description = CATEGORY_UPDATED, content = @Content),
            @ApiResponse(responseCode = STATUS_NOT_FOUND, description = CATEGORY_NOT_FOUND, content = @Content),
            @ApiResponse(responseCode = STATUS_BAD_REQUEST, description = BAD_REQUEST, content = @Content),
            @ApiResponse(responseCode = STATUS_FORBIDDEN, description = FORBIDDEN, content = @Content)
    })
    ResponseEntity<CategoryDTO> updateCategory(@PathVariable Long id,
                                               @Valid @RequestBody CategoryBasicDTO dto);
    }
