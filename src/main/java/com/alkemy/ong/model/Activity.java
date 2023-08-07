package com.alkemy.ong.model;

import lombok.Getter;
import lombok.Setter;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import javax.validation.constraints.NotNull;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import java.sql.Timestamp;

@Entity
@Table(name = "activities")
@SQLDelete(sql = "UPDATE activities SET deleted = true WHERE id=?")
@Where(clause = "deleted=false")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Activity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "{request.name}")
    private String name;

    @NotNull(message = "{request.content}")
    @Lob
    private String content;

    @NotNull(message = "{request.image}")
    private String image;

    @CreationTimestamp
    private Timestamp creationDate;
    @UpdateTimestamp
    private Timestamp updateDate;
    
    private boolean deleted;

}
