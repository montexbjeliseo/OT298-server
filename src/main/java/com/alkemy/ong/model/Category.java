package com.alkemy.ong.model;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.sql.Timestamp;

@Entity
@Table(name = "categories")
@Getter
@Setter
@SQLDelete(sql = "UPDATE categories SET deleted = true WHERE id=?")
@Where(clause = "deleted=false")
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "{request.name}")
    @Pattern(regexp = "^[A-Za-z]*$", message = "{request.letters}")
    private String name;

    private String description;

    private String image;

    @Column(name = "creation_date")
    @CreationTimestamp
    private Timestamp creationDate;

    @Column(name = "update_date")
    @UpdateTimestamp
    private Timestamp updateDate;

    private boolean deleted = Boolean.FALSE;

}
