package com.alkemy.ong.controller;

import com.alkemy.ong.documentation.ICategoryController;
import com.alkemy.ong.dto.CategoryBasicDTO;
import com.alkemy.ong.dto.CategoryCompleteGetDto;
import com.alkemy.ong.dto.CategoryDTO;
import com.alkemy.ong.exception.InvalidPageNumber;
import com.alkemy.ong.service.ICategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.data.domain.Pageable;

import java.util.Locale;
import java.util.Map;

import org.springframework.web.bind.annotation.*;
import static com.alkemy.ong.util.Constants.PAGE_SIZE;

@RestController
@RequestMapping("/categories")
public class CategoryController implements ICategoryController {

    @Autowired
    private ICategoryService categoryService;

    @Autowired
    private MessageSource message;

    @Override
    @GetMapping(params = "page")
    public ResponseEntity<Map<String, Object>> getAllCategories(Integer page){
        if(page<0){
            throw new InvalidPageNumber(message.getMessage("invalid.Page", null, Locale.US));
        }
        Pageable pageable = PageRequest.of(page, PAGE_SIZE);
        Map<String, Object> response = categoryService.getAllCategories(pageable);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @Override
    @GetMapping("/{id}")
    public ResponseEntity<CategoryCompleteGetDto> getCategoryById(Long id){
        CategoryCompleteGetDto category = categoryService.getCategoryById(id);
        return ResponseEntity.status(HttpStatus.OK).body(category);
    }

    @Override
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CategoryDTO> saveCategory (CategoryDTO dto){
        CategoryDTO savedCategory = categoryService.save(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedCategory);
    }

    @Override
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategory(Long id) {
        categoryService.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @Override
    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CategoryDTO> updateCategory(Long id, CategoryBasicDTO dto){
        CategoryDTO categoryUpdated = categoryService.update(id, dto);
        return ResponseEntity.ok().body(categoryUpdated);
    }
}
