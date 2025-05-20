package com.anapaulasantossf.projetos.login_jwt.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class LoginResponseDTO {
    private Long userId;
    private String token;
}
