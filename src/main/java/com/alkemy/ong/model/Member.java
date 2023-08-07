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
import java.time.LocalDate;

@Entity
@Table(name = "members")
@Setter
@Getter
@SQLDelete(sql = "UPDATE members SET deleted = true WHERE id = ?")
@Where(clause = "deleted = false")
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "{request.name}")
    @Pattern(regexp = "^[a-zA-Z][ a-zA-Z]*$", message = "{member.name.pattern.error}")
    private String name;

    @Column(name = "facebook_url")
    private String facebookUrl;

    @Column(name = "instagram_url")
    private String instagramUrl;

    @Column(name = "linkedin_url")
    private String linkedinUrl;

    @NotNull(message = "{request.image}")
    private String image;

    private String description;

    @Column(name = "created_at")
    @CreationTimestamp
    private LocalDate createdAt;

    @Column(name = "updated_at")
    @UpdateTimestamp
    private LocalDate updatedAt;

    private boolean deleted = Boolean.FALSE;
}
