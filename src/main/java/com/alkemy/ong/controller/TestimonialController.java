package com.alkemy.ong.controller;

import com.alkemy.ong.documentation.ITestimonialController;
import com.alkemy.ong.dto.TestimonialBasicDTO;
import com.alkemy.ong.dto.TestimonialDTO;
import com.alkemy.ong.exception.ResourceNotFoundException;
import com.alkemy.ong.service.ITestimonialService;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

import static com.alkemy.ong.util.Constants.Endpoints.*;

@RestController
@RequestMapping(TESTIMONIAL)
public class TestimonialController implements ITestimonialController{
    
    @Autowired
    private ITestimonialService service;

    @GetMapping(PAGE)
    public ResponseEntity<Map<String, Object>> getTestimonialPage(@RequestParam(value = "page", defaultValue = "0") Integer page, Pageable pageable) {
        Map<String, Object> response = service.responseTestimonialPage(page, pageable);
        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> save(@Valid @RequestBody TestimonialDTO dto) throws Exception{
        return new ResponseEntity<>(service.save(dto), HttpStatus.OK);
    }

    @PutMapping(ID)
    public ResponseEntity<TestimonialDTO> updateTestimonial(@PathVariable Long id, @Valid @RequestBody TestimonialBasicDTO dto){
        TestimonialDTO testimonialDTO = service.update(id, dto);
        return new ResponseEntity<TestimonialDTO>(testimonialDTO, HttpStatus.OK);
    }
    
    @DeleteMapping(ID)
    public ResponseEntity<Void> delete (@PathVariable Long id) throws ResourceNotFoundException {
        service.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}