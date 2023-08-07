package com.alkemy.ong.controller;

import com.alkemy.ong.dto.ContactDTO;
import com.alkemy.ong.service.IContactService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

import static com.alkemy.ong.util.Constants.Endpoints.CONTACT;

@RestController
@RequestMapping(CONTACT)
@Tag(name = "Contact")
public class ContactController {

    @Autowired
    private IContactService contactService;

    @PostMapping
    public ResponseEntity<ContactDTO> saveContact(@Valid @RequestBody ContactDTO contactDTO) throws IOException {
        ContactDTO savedContact = contactService.save(contactDTO);
        return ResponseEntity.ok(savedContact);
    }

    @GetMapping
    public ResponseEntity<List<ContactDTO>> getAllContacts () {
        List<ContactDTO> contacts = contactService.getAll();
        return ResponseEntity.status(HttpStatus.OK).body(contacts);
    }
}
