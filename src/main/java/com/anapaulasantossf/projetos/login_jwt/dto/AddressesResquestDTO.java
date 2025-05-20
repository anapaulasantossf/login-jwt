package com.anapaulasantossf.projetos.login_jwt.dto;

import com.anapaulasantossf.projetos.login_jwt.model.User;
import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AddressesResquestDTO {

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

    private User user;
}
