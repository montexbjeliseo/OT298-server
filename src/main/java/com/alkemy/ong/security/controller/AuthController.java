package com.alkemy.ong.security.controller;

import com.alkemy.ong.documentation.IAuthController;
import com.alkemy.ong.security.dto.*;
import com.alkemy.ong.security.service.IUserService;
import org.springframework.http.HttpStatus;
import com.alkemy.ong.security.dto.AuthRequest;
import com.alkemy.ong.security.dto.AuthResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;

@RestController
@RequestMapping("/auth")
public class AuthController implements IAuthController {

    @Autowired
    private IUserService userService;

    @PostMapping("/register")
    public ResponseEntity<UserGetDto> register(@Valid @RequestBody UserPostDto dto) throws IOException {
        UserGetDto userGetDto = userService.registerUser(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(userGetDto);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> signIn(@Valid @RequestBody AuthRequest request) throws Exception {
        AuthResponse response = userService.authenticate(request);

        return ResponseEntity.ok(response);

    }

    @GetMapping("/me")
    public ResponseEntity<UserInformationDto> userInformation(@Valid Authentication authentication) {
        return ResponseEntity.ok(userService.getCurrentAuthenticatedUser(authentication));
    }

}
