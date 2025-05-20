package com.anapaulasantossf.projetos.login_jwt.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserRequestDTO {
    @NotBlank(message = "Email obrigatorio")
    private String email;

    @NotBlank(message = "Senha obrigatoria")
    private String password;

    @NotBlank(message = "Nome obrigatorio")
    private String firstName;

    @NotBlank(message = "Sobrenome obrigatorio")
    private String lastName;

    private String phoneNumber;
}