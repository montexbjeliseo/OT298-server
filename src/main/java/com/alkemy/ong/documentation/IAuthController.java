package com.alkemy.ong.documentation;

import com.alkemy.ong.security.dto.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;
import java.io.IOException;

import static com.alkemy.ong.util.Constants.httpCodes.*;


@Tag(name = "Auth", description = "documentation of authentication endpoints")
public interface IAuthController {
    

    @Operation(summary = "Register user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = STATUS_CREATED, description = "User created successfully", content = @Content),
            @ApiResponse(responseCode = STATUS_BAD_REQUEST, description = "Bad Request", content = @Content),
            @ApiResponse(responseCode = STATUS_NOT_FOUND, description = "Not Found", content = @Content),
            @ApiResponse(responseCode = STATUS_INTERNAL_SERVER_ERROR, description = "Internal Server Error", content = @Content)
    })
    ResponseEntity<UserGetDto> register(@Valid @RequestBody UserPostDto dto) throws IOException;


    @Operation(summary = "Login user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = STATUS_OK, description = "Logged user", content = @Content),
            @ApiResponse(responseCode = STATUS_BAD_REQUEST, description = "Bad Request", content = @Content),
            @ApiResponse(responseCode = STATUS_NOT_FOUND, description = "Not Found", content = @Content),
            @ApiResponse(responseCode = STATUS_FORBIDDEN, description = "Forbidden", content = @Content),
            @ApiResponse(responseCode = STATUS_INTERNAL_SERVER_ERROR, description = "Internal Server Error", content = @Content)
    })
    ResponseEntity<AuthResponse> signIn(@Valid @RequestBody AuthRequest request) throws Exception;



    @Operation(summary = "Get Users")
    @ApiResponses(value = {
            @ApiResponse(responseCode = STATUS_BAD_REQUEST, description = "Bad Request", content = @Content),
            @ApiResponse(responseCode = STATUS_NOT_FOUND, description = "Not Found", content = @Content),
            @ApiResponse(responseCode = STATUS_FORBIDDEN, description = "Forbidden", content = @Content),
            @ApiResponse(responseCode = STATUS_INTERNAL_SERVER_ERROR, description = "Internal Server Error", content = @Content)
    })
    ResponseEntity<UserInformationDto> userInformation(@Valid Authentication authentication);


}