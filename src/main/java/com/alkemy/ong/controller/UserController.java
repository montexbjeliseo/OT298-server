package com.alkemy.ong.controller;

import com.alkemy.ong.documentation.IUserController;
import com.alkemy.ong.dto.UserPatchDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import static com.alkemy.ong.util.Constants.Endpoints.*;

import com.alkemy.ong.security.service.IUserService;
import com.alkemy.ong.exception.ResourceNotFoundException;

import javax.validation.Valid;

@RestController
@RequestMapping(USER)
public class UserController implements IUserController {

    @Autowired
    private IUserService userService;

    @GetMapping(produces = "application/json")
    public ResponseEntity<?> getAll() throws Exception {
        return new ResponseEntity<>(userService.getAllAuxUsers(), HttpStatus.OK);
    }

    @PatchMapping(value = ID, produces = "application/json", consumes = "application/json")
    public ResponseEntity<?> update(@PathVariable Long id, @Valid @RequestBody UserPatchDTO dto) throws ResourceNotFoundException {
            return new ResponseEntity<>(userService.update(id, dto), HttpStatus.OK);
    }

    @DeleteMapping(value = ID, produces = "application/json")
    public ResponseEntity<?> delete(@PathVariable Long id){
        return new ResponseEntity<>(userService.deleteUser(id), HttpStatus.NO_CONTENT);
    }
}
