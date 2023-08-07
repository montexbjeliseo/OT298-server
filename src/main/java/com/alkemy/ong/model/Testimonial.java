package com.alkemy.ong.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.annotations.Where;
import org.springframework.lang.Nullable;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;

@Entity
@Getter
@Setter
@NoArgsConstructor @AllArgsConstructor
@Table(name = "testimonials")
@SQLDelete(sql = "UPDATE testimonials SET deleted = true WHERE id = ?")
@Where(clause = "deleted = false")
public class Testimonial {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "{request.name}")
    private String name;

    @Nullable
    private String image;

    @Nullable
    private String content;

    @Column(name = "creation_date")
    @CreationTimestamp
    private Timestamp creationDate;

    @Column(name = "update_date")
    @UpdateTimestamp
    private Timestamp updateDate;

    private Boolean deleted = Boolean.FALSE;
}
