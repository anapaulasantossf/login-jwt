package com.anapaulasantossf.projetos.login_jwt.dto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AddressesResponseDTO {
    private Long id;
    private String zipCode;
    private String  street;
    private Long number;
    private String complement;
    private String state;
    private String city;
}
