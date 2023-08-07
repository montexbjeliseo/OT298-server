package com.alkemy.ong.repository;

import com.alkemy.ong.model.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Pageable;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

    boolean existsById(Long id);
    Page<Category> findAll(Pageable pageable);
}
