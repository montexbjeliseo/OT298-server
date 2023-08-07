package com.alkemy.ong.security.service.impl;

import com.alkemy.ong.dto.UserPatchDTO;
import com.alkemy.ong.exception.*;
import com.alkemy.ong.dto.AuxUserGetDto;
import com.alkemy.ong.model.Comment;
import com.alkemy.ong.security.dto.UserGetDto;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.authentication.BadCredentialsException;
import com.alkemy.ong.exception.AlreadyExistsException;
import com.alkemy.ong.exception.ParameterNotFound;
import com.alkemy.ong.exception.ResourceNotFoundException;
import com.alkemy.ong.security.dto.*;
import com.alkemy.ong.security.service.*;
import com.alkemy.ong.security.model.*;
import com.alkemy.ong.security.repository.UserRepository;
import com.alkemy.ong.security.repository.RoleRepository;
import com.alkemy.ong.service.IEmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import com.alkemy.ong.security.dto.UserPostDto;
import com.alkemy.ong.security.mapper.UserMapper;

import static com.alkemy.ong.util.Constants.ROLE_ADMIN;
import static com.alkemy.ong.util.Constants.ROLE_USER;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.*;
import java.io.IOException;

@Service
public class UserServiceImpl implements IUserService, UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private IEmailService emailService;

    @Autowired
    private MessageSource message;

    @Override
    public List<UserGetDto> getAllUsers() {
        List<User> users = userRepository.findAll();
        List<UserGetDto> usersDtos = userMapper.listUsersToListDtos(users);
        return usersDtos;
    }

    @Override
    public List<AuxUserGetDto> getAllAuxUsers() {
        List<User> users = userRepository.findAll();
        List<AuxUserGetDto> usersDtos = userMapper.toAuxList(users);
        return usersDtos;
    }

    @Override
    public AuthResponse authenticate(AuthRequest request) throws ParameterNotFound {
        if(!userRepository.existsByEmail(request.getUsername())) {
            throw new UsernameNotFoundException("Username not found");
        }
        UserDetails userDetails;
        try {
            Authentication auth = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
            );
            userDetails = (UserDetails) auth.getPrincipal();
        } catch (BadCredentialsException e) {
            throw new ParameterNotFound(message.getMessage("credentials.incorrect", null, Locale.US));
        }
        final String jwt = jwtUtils.generateToken(userDetails);
        return new AuthResponse(jwt);
    }

    @Override
    @Transactional
    public UserGetDto saveTestDataUser(UserPostDto dto) {

        if(userRepository.existsByEmail(dto.getEmail())) {
            throw new AlreadyExistsException(
                    message.getMessage("email.exists", null, Locale.US));
        }

        User user = userMapper.userPostDtoToUser(dto);
        user.setPassword(passwordEncoder.encode(dto.getPassword()));

        User savedUser = userRepository.save(user);

        UserGetDto userGetDto = userMapper.userToUserDto(savedUser);

        List<User> users = userRepository.findAll();
        for (User userGet : users) {
            if (userGet.getId() >= 1 && userGet.getId() <= 10) {
                this.addRoleToUser(ROLE_ADMIN, user);
            } else {
                this.addRoleToUser(ROLE_USER, user);
            }
        }

        return userGetDto;
    }

    @Override
    @Transactional
    public UserGetDto registerUser(UserPostDto dto) throws IOException {

        if(userRepository.existsByEmail(dto.getEmail())) {
            throw new AlreadyExistsException(
                    message.getMessage("email.exists", null, Locale.US));
        }

        User user = userMapper.userPostDtoToUser(dto);
        user.setPassword(passwordEncoder.encode(dto.getPassword()));

        User savedUser = userRepository.save(user);

        addRoleToUser(ROLE_USER, savedUser);

        UserGetDto userGetDto = userMapper.userToUserDto(savedUser);

        Authentication auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(dto.getEmail(), dto.getPassword())
        );

        UserDetails userDetails = (UserDetails) auth.getPrincipal();
        final String jwt = jwtUtils.generateToken(userDetails);
        userGetDto.setJwtToken(jwt);

        emailService.sendWelcomeEmail(user.getEmail());

        return userGetDto;
    }

    @Override
    @Transactional
    public AuxUserGetDto deleteUser(Long id) {
        Optional<User> user = userRepository.findById(id);
        if (!user.isPresent()){
            throw new ParameterNotFound(message.getMessage("id.invalid", null, Locale.US));
        }
        String target = user.get().getEmail();
        String logged = SecurityContextHolder.getContext().getAuthentication().getName();
        if (!logged.equals(target)){
            throw new MismatchException(message.getMessage("mismatch.users",null,Locale.US));
        }
        userRepository.deleteById(id);
        return userMapper.toAuxDto(user.get());
    }

    @Override
    @Transactional
    public void addRoleToUser(String nameRole, User user) {
        Role role = roleRepository.findByName(nameRole);
        Set<Role> roles = new HashSet<>();
        roles.add(role);
        user.setRoles(roles);

        role.getUsers().add(user);
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        User user = userRepository.findByEmail(email);
        if (user == null) {

            throw new UsernameNotFoundException(message.getMessage("email.notFound", null, Locale.US));
        }
        List<GrantedAuthority> authorities = new ArrayList<>();

        for (Role role : user.getRoles()) {
            authorities.add(new SimpleGrantedAuthority(role.getName()));
        }

        org.springframework.security.core.userdetails.User userDetails = new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), authorities);
        return userDetails;
    }

    @Override
    public AuxUserGetDto update(Long id, UserPatchDTO dto) throws ResourceNotFoundException {
        if (!userRepository.existsById(id)) {
            throw new ResourceNotFoundException(message.getMessage("id.invalid", null, Locale.US));
        }
        User user = userRepository.findById(id).get();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (user.getEmail().equals(auth.getName()) || auth.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals(ROLE_ADMIN))) {
            if (dto.getFirstName() != null) {
                user.setFirstName(dto.getFirstName());
            }

            if (dto.getLastName() != null) {
                user.setLastName(dto.getLastName());
            }

            if (dto.getPhoto() != null) {
                user.setPhoto(dto.getPhoto());
            }

            if (dto.getPassword() != null) {
                user.setPassword(passwordEncoder.encode(dto.getPassword()));
            }

            return userMapper.toAuxDto(userRepository.save(user));
        } else {
            throw new PermissionDeniedException(message.getMessage("access.denied", null, Locale.US));
        }
    }

    @Override
    public UserInformationDto getCurrentAuthenticatedUser(Authentication authentication) {
        try {

            User user= getUserAuthenticated(authentication);

            return userMapper.userToUserInformationDto(user);

        } catch (io.jsonwebtoken.SignatureException e) {
            throw new InvalidTokenException(message.getMessage("invalid.token", null, Locale.US));
        }
    }



    public User getUserAuthenticated(Authentication authentication) {
        if (authentication == null) {
            throw new NoAuthorizationProvidedException(message.getMessage("request.authorizationNotProvided", null, Locale.US));
        }
        if (!authentication.isAuthenticated()) {
            throw new BadCredentialsException(message.getMessage("user.notAuthenticated", null, Locale.US));
        }
        User user = userRepository.findByEmail((String) authentication.getPrincipal());

        if (user == null) {
            throw new UsernameNotFoundException(message.getMessage("email.notFound", null, Locale.US));
        }
        return user;
    }
}
