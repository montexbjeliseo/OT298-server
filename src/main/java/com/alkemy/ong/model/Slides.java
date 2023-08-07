package com.alkemy.ong.model;
import lombok.Getter;
import lombok.Setter;
import javax.persistence.*;

@Entity
@Table(name = "slides")
@Getter @Setter
public class Slides {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String image;
    private String text;
    private Integer position;

    @ManyToOne
    @JoinColumn(name = "organization_id", insertable = false, updatable = false)
    private Organization organization;

    @Column(name = "organization_id")
    private Long organizationId;

}
