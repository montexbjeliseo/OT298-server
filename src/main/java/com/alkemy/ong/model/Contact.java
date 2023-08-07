package com.alkemy.ong.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.annotations.Where;
import org.springframework.lang.Nullable;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.sql.Timestamp;

@Data
@NoArgsConstructor
@AllArgsConstructor
@SQLDelete(sql = "UPDATE contacts SET deleted = true WHERE id=?")
@Where(clause= "deleted = false")
@Entity
@Table(name = "contacts")
public class Contact {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "contact_id")
    private Long id;

    @NotBlank(message = "Name is required")
    @Pattern(regexp = "^[A-Za-z\\s]*$")
    private String name;

    @NotBlank(message = "Phone is required")
    @Pattern(regexp = "^[0-9]{10}$")
    private String phone;

    @NotNull(message = "Email is required")
    @Email
    private String email;

    @NotNull(message = "Message is required")
    private String message;

    @Nullable
    private boolean deleted = Boolean.FALSE;

    @CreationTimestamp
    @Column(name="creation_date", updatable = false)
    private Timestamp creationDate;

    @UpdateTimestamp
    @Column(name="last_updated")
    private Timestamp lastUpdated;
}
