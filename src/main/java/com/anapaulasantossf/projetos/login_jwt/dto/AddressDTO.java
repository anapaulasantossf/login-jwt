package com.anapaulasantossf.projetos.login_jwt.dto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class AddressDTO {

    private Long id;

    @Column(name = "zipCode")
    private String zipCode;

    @NotBlank(message = "Endereço obrigatorio")
    private String  street;

    @NotNull(message = "Numero do obrigatorio")
    private Long number;

    private String complement;

    @NotBlank(message = "Estado obrigatorio")
    private String state;

    @NotBlank(message = "Cidade obrigatorio")
    private String city;

    @NotNull(message = "Usuario obrigatorio")
    private Long userId;
}
