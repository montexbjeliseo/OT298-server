package com.alkemy.ong.security.model;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Entity
@Table(name = "roles")
@Getter
@Setter
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @NotNull(message = "Name may not be null")
    private String name;

    private String description;

    @Column(name = "creation_date")
    @Temporal(TemporalType.TIMESTAMP)
    @CreationTimestamp
    private Date creationDate;

    @Column(name = "last_update")
    @Temporal(TemporalType.TIMESTAMP)
    @UpdateTimestamp
    private Date lastUpdate;

    @ManyToMany(mappedBy = "roles", fetch = FetchType.LAZY)
    private List<User> users = new ArrayList<>();

    //TODO : finish method
    public Role get() {

        return null;
    }

    public Role(){

    }
    public Role (Long id, String name){
        this.id = id;
        this.name = name;
    }

}
