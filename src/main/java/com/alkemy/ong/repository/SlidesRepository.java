package com.alkemy.ong.repository;

import com.alkemy.ong.model.Slides;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface SlidesRepository extends JpaRepository<Slides, Long> {


    @Query("SELECT s FROM Slides s WHERE s.organizationId = :orgId ORDER BY s.position")
    List<Slides> findAllByOrganization(@Param("orgId") Long organizationId);
}
