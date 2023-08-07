package com.alkemy.ong.documentation;

import com.alkemy.ong.dto.MemberDTO;
import com.alkemy.ong.dto.MemberPaginationDTO;
import com.alkemy.ong.dto.NewsDto;
import com.alkemy.ong.dto.TestimonialDTO;
import com.alkemy.ong.exception.ResourceNotFoundException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import static com.alkemy.ong.util.Constants.MemberApi.*;
import static com.alkemy.ong.util.Constants.httpCodes.*;
import static com.alkemy.ong.util.Pagination.INITIAL_PAGE;
import static com.alkemy.ong.util.Pagination.PAGE_SIZE;

@Tag(name = TAG_NAME, description = TAG_DESCRIPTION)
public interface IMemberController {

    @Operation(summary = SUMARY_ADD, description = DESCRIPTION_ADD)
    @ApiResponses(value = {
            @ApiResponse(responseCode = STATUS_OK, description = SUCCESS, content = {
                    @Content(schema = @Schema(implementation = MemberDTO.class))}),
            @ApiResponse(responseCode = STATUS_BAD_REQUEST, description = INVALID_DATA, content = {@Content}),
            @ApiResponse(responseCode = STATUS_FORBIDDEN, description = NO_AUTHORIZATION, content = {@Content}),
            @ApiResponse(responseCode = STATUS_INTERNAL_SERVER_ERROR, description = ERROR_SERVER_ADD, content = {@Content})
    })
    ResponseEntity<MemberDTO> save(@Parameter(description = PARAMETER_MEMBER_ADD) @Valid @RequestBody MemberDTO dto);

    @Operation(summary = SUMARY_UPDATE, description = DESCRIPTION_UPDATE)
    @ApiResponses(value = {
            @ApiResponse(responseCode = STATUS_OK, description = SUCCESS, content = {
                    @Content(schema = @Schema(implementation = MemberDTO.class))}),
            @ApiResponse(responseCode = STATUS_BAD_REQUEST, description = INVALID_DATA, content = {@Content}),
            @ApiResponse(responseCode = STATUS_NOT_FOUND, description = NOT_FOUND_MEMBER, content = {@Content}),
            @ApiResponse(responseCode = STATUS_FORBIDDEN, description = NO_AUTHORIZATION, content = {@Content}),
            @ApiResponse(responseCode = STATUS_INTERNAL_SERVER_ERROR, description = ERROR_SERVER_UPDATE, content = {@Content})
    })
    ResponseEntity<MemberDTO> update(@Parameter(description = PARAMETER_ID) @PathVariable("id") Long id,
                                     @Parameter(description = PARAMETER_MEMBER_UPDATE) @Valid @RequestBody MemberDTO dto);

    @Operation(summary = SUMARY_DELETE, description = DESCRIPTION_DELETE)
    @ApiResponses(value = {
            @ApiResponse(responseCode = STATUS_OK, description = SUCCESS, content = {@Content}),
            @ApiResponse(responseCode = STATUS_NOT_FOUND, description = NOT_FOUND_MEMBER, content = {@Content}),
            @ApiResponse(responseCode = STATUS_FORBIDDEN, description = NO_AUTHORIZATION, content = {@Content})
    })
    ResponseEntity<?> delete(@Parameter(description = PARAMETER_ID) @PathVariable Long id) throws ResourceNotFoundException;

    @Operation(summary = SUMARY_GET_ALL, description = DESCRIPTION_GET_ALL)
    @ApiResponses(value = {
            @ApiResponse(responseCode = STATUS_OK, description = SUCCESS, content = {
                    @Content(schema = @Schema(implementation = MemberDTO.class))}),
            @ApiResponse(responseCode = STATUS_NOT_FOUND, description = THERE_ARE_NO_MEMBER, content = {@Content}),
            @ApiResponse(responseCode = STATUS_FORBIDDEN, description = NO_AUTHORIZATION, content = {@Content})
    })
    ResponseEntity<?> getAll();

    @Operation(summary = SUMARY_PAGINATION, description = DESCRIPTION_PAGINATION)
    @ApiResponses(value = {
            @ApiResponse(responseCode = STATUS_OK, description = SUCCESS, content = {
                    @Content(schema = @Schema(implementation = MemberPaginationDTO.class))}),
            @ApiResponse(responseCode = STATUS_BAD_REQUEST, description = INVALID_DATA, content = {@Content}),
            @ApiResponse(responseCode = STATUS_FORBIDDEN, description = NO_AUTHORIZATION, content = {@Content})
    })
    ResponseEntity<MemberPaginationDTO> getPaginated(
            @Parameter(description = PARAMETER_MEMBER_PAGE) @Valid @PageableDefault(page = INITIAL_PAGE, size = PAGE_SIZE) Pageable pageable,
            HttpServletRequest request,
            UriComponentsBuilder uriBuilder);


}
