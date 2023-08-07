package com.alkemy.ong.security.model;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.annotations.Where;
import org.springframework.lang.Nullable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


@Entity
@Table(name="users")
@Getter
@Setter
@SQLDelete(sql = "UPDATE users SET deleted = true WHERE id=?")
@Where(clause= "deleted = false")

public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "First name is required")
    @Pattern(regexp = "^[a-zA-Z][ a-zA-Z]*$", message = "{user.name.pattern}")
    @Column(name="first_name", nullable = false)
    private String firstName;

    @NotBlank (message = "Last name is required")
    @Pattern(regexp = "^[a-zA-Z][ a-zA-Z]*$", message = "{user.lastname.pattern}")
    @Column(name="last_name", nullable = false)
    private String lastName;

    @Email (message = "{email.format}")
    @Column(unique = true, nullable = false)
    private String email;

    @NotBlank(message = "Password is required")
    @Column(nullable = false)
    private String password;

    @Nullable
    private String photo;

    @Nullable
    private boolean deleted = Boolean.FALSE;

    @CreationTimestamp
    @Column(name="creation_date", updatable = false)
    private Timestamp creationDate;

    @UpdateTimestamp
    @Column(name="last_updated")
    private Timestamp lastUpdated;

    @ManyToMany(fetch =
            FetchType.EAGER,
            cascade = CascadeType.PERSIST)

    @JoinTable(name = "user_role",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))

    private Set<Role> roles = new HashSet<>();




    private boolean accountNonExpired;

    private boolean accountNonLocked;

    private boolean credentialsNonExpired;

    private boolean enabled;




    public User() {
        this.accountNonExpired = true;
        this.accountNonLocked = true;
        this.credentialsNonExpired = true;
        this.enabled = true;
    }

    public User(Long id, String firstName, String lastName, String email, String password, Set<Role> roles){
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.roles = roles;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.getRoles().stream()
                    .map(role -> new SimpleGrantedAuthority(role.getName()))
                    .collect(Collectors.toList());
    }

    @Override
    public String getUsername() {
        return this.email;
    }


    public void setRole(Role role) {

    }

    public void addRole(Role role){
        roles.add(role);
        role.getUsers().add(this);
    }

    public Set<Role> getRoles() {
        return roles;
    }
}
