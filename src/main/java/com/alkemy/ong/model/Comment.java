package com.alkemy.ong.model;

import com.alkemy.ong.security.model.User;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.sql.Timestamp;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "comments")
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    private String body;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "user_id", updatable = false)
    private User user;


    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "news_id", updatable = false)
    @JsonIgnoreProperties("comments")
    private News news;

    @Column(name = "creation_date")
    @CreationTimestamp
    private Timestamp creationDate;

    @Column(name = "update_date")
    @UpdateTimestamp
    private Timestamp updateDate;

}
