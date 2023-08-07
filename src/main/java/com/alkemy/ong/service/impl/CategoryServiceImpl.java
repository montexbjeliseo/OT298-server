package com.alkemy.ong.service.impl;


import com.alkemy.ong.dto.CategoryBasicDTO;
import com.alkemy.ong.dto.CategoryCompleteGetDto;
import com.alkemy.ong.dto.CategoryGetDto;
import com.alkemy.ong.exception.InvalidPageNumber;
import com.alkemy.ong.exception.PageNotFound;
import com.alkemy.ong.exception.ResourceNotFoundException;
import com.alkemy.ong.exception.ThereAreNoCategories;
import com.alkemy.ong.dto.CategoryDTO;

import com.alkemy.ong.mapper.CategoryMapper;
import com.alkemy.ong.model.Category;
import com.alkemy.ong.repository.CategoryRepository;
import com.alkemy.ong.service.ICategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Pageable;

import java.util.*;

import javax.transaction.Transactional;


@Service
public class CategoryServiceImpl implements ICategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private CategoryMapper categoryMapper;

    @Autowired
    private MessageSource message;

    @Override
    public Map<String, Object> getAllCategories(Pageable pageable) {

        if(categoryRepository.findAll().isEmpty()){
            throw new ThereAreNoCategories(
                    message.getMessage("category.thereAreNo", null, Locale.US));
        }

        Page<Category> pageCategories = categoryRepository.findAll(pageable);

        if(pageable.getPageNumber()>=pageCategories.getTotalPages()){
            throw new PageNotFound(
                    message.getMessage("page.NotFound", null, Locale.US)
                    + ". Total pages: " + pageCategories.getTotalPages()
            );
        }

        List<Category> categories = pageCategories.getContent();
        List<CategoryGetDto> listDtosCategories = categoryMapper.listCategoriesToListDtos(categories);

        Map<String, Object> response = new HashMap<>();
        response.put("categories", listDtosCategories);

        if(pageCategories.getPageable().next().getPageNumber() < pageCategories.getTotalPages()) {
            String nextPage = String.format("http://localhost:8080/cateogires?page=%s",
                    String.valueOf(pageCategories.getPageable().next().getPageNumber()));
            response.put("next page", nextPage);
        }else {
            response.put("next page", null);
        }

        if(pageable.hasPrevious()) {
            String previousPage = String.format("http://localhost:8080/cateogires?page=%s",
                    String.valueOf(pageable.previousOrFirst().getPageNumber()));
            response.put("previous page", previousPage);
        }else{
            response.put("previous page", null);
        }

        return response;
    }

    @Override
    public CategoryCompleteGetDto getCategoryById(Long id) {
        if(!categoryRepository.existsById(id)){
            throw new ResourceNotFoundException (message.getMessage("category.notFound", null, Locale.US));
        }
        Category category = categoryRepository.findById(id).get();
        CategoryCompleteGetDto categoryDto = categoryMapper.categoryToCategoryCompleteGetDto(category);
        return categoryDto;
    }

    @Transactional
    @Override
    public CategoryDTO save (CategoryDTO dto){
            Category category = categoryMapper.categoryDTOToCategory(dto);
            Category savedCategory = categoryRepository.save(category);
            return categoryMapper.categoryToCategoryDTO(savedCategory);
    }

    public void delete (Long id){
        Optional<Category> response = categoryRepository.findById(id);
        if(response.isPresent()) {
            categoryRepository.deleteById(id);
        }else{
                throw new ResourceNotFoundException(message.getMessage("category.notFound", null, Locale.US));
        }
    }

    @Transactional
    public CategoryDTO update (Long id, CategoryBasicDTO dto){
        Optional<Category> response = categoryRepository.findById(id);
        if(response.isPresent()){
            Category category = response.get();
            category = categoryRepository.save(categoryMapper.updateCategoryFromDto(dto, category));
            return categoryMapper.categoryToCategoryDTO(category);
        }else {
            throw new ResourceNotFoundException(message.getMessage("category.notFound", null, Locale.US));
        }
    }
}
