package com.alkemy.ong.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.sql.Timestamp;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CategoryCompleteGetDto {

    private Long id;

    private String name;

    private String description;

    private String image;

    @CreationTimestamp
    private Timestamp creationDate;

    @UpdateTimestamp
    private Timestamp updateDate;

    private boolean deleted;
}
