package com.anapaulasantossf.projetos.login_jwt.controller;

import com.anapaulasantossf.projetos.login_jwt.dto.LoginRequestDTO;
import com.anapaulasantossf.projetos.login_jwt.dto.LoginResponseDTO;
import com.anapaulasantossf.projetos.login_jwt.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/auth")
@RestController
public class LoginController {

    @Autowired
    LoginService loginService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody LoginRequestDTO loginRequestDTO) {

        LoginResponseDTO loginResponseDTO = loginService.findByEmail(loginRequestDTO);
        return ResponseEntity.ok(loginResponseDTO);
    }
}
