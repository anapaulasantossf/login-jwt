package com.anapaulasantossf.projetos.login_jwt.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class LoginRequestDTO {

    @NotBlank(message = "Email obrigatorio")
    private String email;

    @NotBlank(message =  "Senha Obrigatorio")
    private String password;
}