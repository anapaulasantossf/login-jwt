package com.anapaulasantossf.projetos.login_jwt.service;

import com.anapaulasantossf.projetos.login_jwt.dto.LoginResponseDTO;
import com.anapaulasantossf.projetos.login_jwt.model.User;
import com.anapaulasantossf.projetos.login_jwt.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Service
public class LoginService {

    @Autowired
    UserRepository userRepository;

    public LoginResponseDTO findByEmail(String email) {

        Optional<User> user = userRepository.findByEmail(email);

        if (user.isPresent()) {
            boolean correto = true;//passwordEncoder.matches(loginDTO.getPassword(), usuario.get().getSenha());

            LoginResponseDTO loginResponseDTO = new LoginResponseDTO();
            loginResponseDTO.setUserId(user.get().getId());
            loginResponseDTO.setToken("TesteToken");

            if (correto) {
                return loginResponseDTO;
            } else {
                throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Usuário inválido");
            }
        }
        throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Usuário inválido");
    }


}
