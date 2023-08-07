package com.alkemy.ong.controller;


import com.alkemy.ong.dto.ActivityBasicDTO;
import com.alkemy.ong.dto.ActivityDTO;
import com.alkemy.ong.service.IActivityService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@Validated
@RequestMapping("/activities")
@Tag(name = "Activity")
public class ActivityController {

    @Autowired
    private IActivityService activityService;

    @PostMapping
    public ResponseEntity<ActivityDTO> saveActivity (@Valid @RequestBody ActivityDTO dto){
        ActivityDTO savedActivity = activityService.save(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedActivity);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ActivityDTO> updateActivity(@PathVariable Long id, @Valid @RequestBody ActivityBasicDTO dto) {
        ActivityDTO activityDTO = activityService.update(id, dto);
        return ResponseEntity.ok().body(activityDTO);
    }
}
