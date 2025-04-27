package com.anapaulasantossf.projetos.login_jwt.service;

import com.anapaulasantossf.projetos.login_jwt.config.JWTService;
import com.anapaulasantossf.projetos.login_jwt.dto.LoginRequestDTO;
import com.anapaulasantossf.projetos.login_jwt.dto.LoginResponseDTO;
import com.anapaulasantossf.projetos.login_jwt.model.User;
import com.anapaulasantossf.projetos.login_jwt.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Service
public class LoginService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    JWTService jwtService;

    public LoginResponseDTO findByEmail(LoginRequestDTO loginRequestDTO) {

        Optional<User> user = userRepository.findByEmail(loginRequestDTO.getEmail());

        if (user.isPresent()) {
            boolean correto = passwordEncoder.matches(loginRequestDTO.getPassword(), user.get().getPassword());

            if (correto) {
                String token = jwtService.generateToken(user.get());
                LoginResponseDTO loginResponseDTO = new LoginResponseDTO();
                loginResponseDTO.setUserId(user.get().getId());
                loginResponseDTO.setToken(token);
                return loginResponseDTO;
            } else {
                throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Usuário inválido");
            }
        }
        throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Usuário inválido");
    }

}
