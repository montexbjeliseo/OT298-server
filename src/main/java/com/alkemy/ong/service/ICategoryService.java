package com.alkemy.ong.service;


import com.alkemy.ong.dto.CategoryBasicDTO;
import com.alkemy.ong.dto.CategoryCompleteGetDto;
import com.alkemy.ong.dto.CategoryGetDto;
import com.alkemy.ong.dto.CategoryDTO;
import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

public interface ICategoryService {

    @Transactional
    CategoryDTO save(CategoryDTO dto);

    Map<String, Object> getAllCategories(Pageable pageable);

    CategoryCompleteGetDto getCategoryById(Long id);
    void delete(Long id);
    CategoryDTO update(Long id, CategoryBasicDTO dto);
}
