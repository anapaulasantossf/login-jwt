package com.anapaulasantossf.projetos.login_jwt.controller;

import com.anapaulasantossf.projetos.login_jwt.dto.LoginRequestDTO;
import com.anapaulasantossf.projetos.login_jwt.dto.LoginResponseDTO;
import com.anapaulasantossf.projetos.login_jwt.service.LoginService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/auth")
@RestController
public class LoginController {

    @Autowired
    LoginService loginService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@Valid @RequestBody LoginRequestDTO loginRequestDTO) {
        LoginResponseDTO loginResponseDTO = loginService.findByEmail(loginRequestDTO);
        return ResponseEntity.ok(loginResponseDTO);
    }
}
