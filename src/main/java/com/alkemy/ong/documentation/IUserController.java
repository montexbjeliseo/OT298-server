package com.alkemy.ong.documentation;

import com.alkemy.ong.dto.AuxUserGetDto;
import com.alkemy.ong.dto.UserPatchDTO;
import com.alkemy.ong.exception.ResourceNotFoundException;
import com.alkemy.ong.security.dto.UserGetDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;

import static com.alkemy.ong.util.Constants.UserApi.*;
import static com.alkemy.ong.util.Constants.httpCodes.*;

@Tag(name = TAG_NAME, description = TAG_DESCRIPTION)
public interface IUserController {

    @Operation(summary = SUMARY_GET_ALL, description = DESCRIPTION_GET_ALL)
    @ApiResponses(value = {
            @ApiResponse(responseCode = STATUS_OK, description = SUCCESS, content = {
                    @Content(schema = @Schema(implementation = UserGetDto.class))}),
            @ApiResponse(responseCode = STATUS_NOT_FOUND, description = THERE_ARE_NO_USERS, content = {@Content}),
            @ApiResponse(responseCode = STATUS_FORBIDDEN, description = NO_AUTHORIZATION, content = {@Content})
    })
    ResponseEntity<?> getAll() throws Exception;


    @Operation(summary = SUMARY_UPDATE, description = DESCRIPTION_UPDATE)
    @ApiResponses(value = {
            @ApiResponse(responseCode = STATUS_OK, description = SUCCESS, content = {
                    @Content(schema = @Schema(implementation = AuxUserGetDto.class))}),
            @ApiResponse(responseCode = STATUS_BAD_REQUEST, description = INVALID_DATA, content = {@Content}),
            @ApiResponse(responseCode = STATUS_NOT_FOUND, description = NOT_FOUND_USER, content = {@Content}),
            @ApiResponse(responseCode = STATUS_FORBIDDEN, description = NO_AUTHORIZATION, content = {@Content}),
            @ApiResponse(responseCode = STATUS_INTERNAL_SERVER_ERROR, description = ERROR_SERVER_UPDATE, content = {@Content})
    })
    ResponseEntity<?> update(@Parameter(description = PARAMETER_ID) @PathVariable Long id,
                             @Parameter(description = PARAMETER_USER_UPDATE) @Valid @RequestBody UserPatchDTO dto) throws ResourceNotFoundException;


    @Operation(summary = SUMARY_DELETE, description = DESCRIPTION_DELETE)
    @ApiResponses(value = {
            @ApiResponse(responseCode = STATUS_OK, description = SUCCESS, content = {
                    @Content(schema = @Schema(implementation = AuxUserGetDto.class))}),
            @ApiResponse(responseCode = STATUS_NOT_FOUND, description = NOT_FOUND_USER, content = {@Content}),
            @ApiResponse(responseCode = STATUS_FORBIDDEN, description = NO_AUTHORIZATION, content = {@Content})
    })
    ResponseEntity<?> delete(@Parameter(description = PARAMETER_ID) @PathVariable Long id);
}
