package com.korobko.models;

import lombok.Builder;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Entity
@Data
@Builder
public class Student implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotNull
    @Size(min = 3, max = 64)
    @Column(nullable = false)
    private String firstName;

    @NotNull
    @Size(min = 3, max = 64)
    @Column(nullable = false)
    private String lastName;

    @ManyToOne
    @JoinColumn(name = "university_id")
    private University university;

}
