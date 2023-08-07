package com.alkemy.ong.security.service;

import com.alkemy.ong.dto.AuxUserGetDto;
import com.alkemy.ong.dto.UserPatchDTO;
import com.alkemy.ong.exception.ParameterNotFound;
import com.alkemy.ong.exception.ResourceNotFoundException;
import com.alkemy.ong.security.dto.*;
import com.alkemy.ong.security.model.User;
import org.springframework.security.core.Authentication;


import java.io.IOException;
import java.util.List;

public interface IUserService {

    AuthResponse authenticate(AuthRequest request) throws ParameterNotFound;

    void addRoleToUser (String nameRole, User user);

    UserGetDto saveTestDataUser(UserPostDto dto);

    List<UserGetDto> getAllUsers();

    UserGetDto registerUser(UserPostDto dto) throws IOException;
    
    List<AuxUserGetDto> getAllAuxUsers();
    
    AuxUserGetDto update(Long id, UserPatchDTO dto) throws ResourceNotFoundException;

    AuxUserGetDto deleteUser(Long id);

    UserInformationDto getCurrentAuthenticatedUser(Authentication authentication);

    User getUserAuthenticated(Authentication authentication);
}
