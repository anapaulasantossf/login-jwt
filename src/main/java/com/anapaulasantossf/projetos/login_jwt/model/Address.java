package com.anapaulasantossf.projetos.login_jwt.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;


@Table (name = "address")
@Entity (name = "address")
@NoArgsConstructor
@Data
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "zipCode")
    private String zipCode;

    @NotBlank
    private String  street;

    @NotNull
    private Long number;

    private String complement;

    @NotBlank
    private String state;

    @NotBlank
    private String city;

    @ManyToOne
    @JoinColumn(name = "userId", nullable = false)
    private User user;
}
