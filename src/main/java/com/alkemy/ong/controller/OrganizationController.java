package com.alkemy.ong.controller;

import com.alkemy.ong.dto.OrganizationBasicDTO;
import com.alkemy.ong.dto.OrganizationUpdateDTO;
import com.alkemy.ong.service.IOrganizationService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("organization")
@Tag(name = "Organization")
public class OrganizationController {

    @Autowired
    private IOrganizationService organizationService;

    @GetMapping("/public")
    public ResponseEntity<OrganizationBasicDTO> getOrganizationBasic() {
        return ResponseEntity.ok(organizationService.getOrganizationBasic());
    }

    @PatchMapping("/public/{id}")
    public ResponseEntity<OrganizationUpdateDTO> putOrganization(@PathVariable Long id, @Valid @RequestBody OrganizationUpdateDTO dto) {
        OrganizationUpdateDTO updatedOrganization = organizationService.updateOrganization(id, dto);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(updatedOrganization);
    }
}
