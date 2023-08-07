package com.alkemy.ong.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name= "news")
@Getter
@Setter
@SQLDelete(sql= "Update news SET deleted = true WHERE id=?")
@Where(clause= "deleted=false")

public class News {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "{request.name}")
    private String name;


    @NotNull(message = "{request.content}")
    private String content;


    @NotNull(message = "{request.image}")
    private String image;

    @Column(name="category_id")
    private Long categoryId;

    @Column(name = "creation_date")
    @CreationTimestamp
    private LocalDateTime creationDate;

    @Column(name = "update_date")
    @UpdateTimestamp
    private LocalDateTime updateDate;

    private boolean deleted = Boolean.FALSE;

    @OneToMany(mappedBy = "news")
    @JsonIgnoreProperties("news")
    private List<Comment> comments;

    public News(){

    }

    public News(Long id, String name, String content, String image, Long categoryId, LocalDateTime creationDate, LocalDateTime updateDate, boolean deleted) {
        this.id = id;
        this.name = name;
        this.content = content;
        this.image = image;
        this.categoryId = categoryId;
        this.creationDate = creationDate;
        this.updateDate = updateDate;
        this.deleted = deleted;
    }
}
