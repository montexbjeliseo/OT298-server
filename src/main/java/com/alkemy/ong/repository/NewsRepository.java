package com.alkemy.ong.repository;

import com.alkemy.ong.model.Comment;
import com.alkemy.ong.model.News;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NewsRepository extends JpaRepository<News, Long> {

    boolean existsById(Long id);

    Page<News> findAll(Pageable pageable);

    @Query("SELECT c FROM News n JOIN n.comments c WHERE n.id = :id")
    List<Comment> findCommentsByNewsId(@Param("id") Long id);
}
