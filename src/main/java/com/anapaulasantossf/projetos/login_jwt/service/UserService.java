package com.anapaulasantossf.projetos.login_jwt.service;

import com.anapaulasantossf.projetos.login_jwt.model.User;
import com.anapaulasantossf.projetos.login_jwt.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;



    public Optional<User> findById(Long id) {
        return Optional.ofNullable(userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User not found with id " + id)));
    }

    public List<User> findAll() {
        //String token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJsb2dpbi1qd3QtYXBpIiwic3ViIjoiVG9rZW4gZGUgYXV0ZW50aWNhw6fDo28gZGEgQVBJIiwiaWQiOjQsImVtYWlsIjoiYW5hcGF1bGExQHRlc3RlLmNvbS5iciIsImZpcnN0TmFtZSI6IkFuYSIsImxhc3ROYW1lIjoiRmVycmVpcmEiLCJwaG9uZU51bWJlciI6IjMxOTk5OTk5OTk5IiwiZXhwIjoxNzQ1Nzk3NDQ1fQ.yp0YjNh9K0f86KlKQ726J5crrmRTH0rLCbxeqvGwHLU";
        //String teste = jwtService.validateToken(token);
        //Map<String, Claim> teste2 = jwtService.validateToken2(token);
        //System.out.println("AQUIIIII: " + teste);
        //System.out.println("XXXXXX teste2: " + teste2);
        return userRepository.findAll();
    }

    public User create(User user) {

        String hashPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(hashPassword);

        return userRepository.save(user);
    }

    public User update(Long id, User user) {
        try {
            Optional<User> usuarioOptional = this.findById(id);

            if (usuarioOptional.isPresent()) {
                return userRepository.saveAndFlush(user);
            } else {
                throw new EntityNotFoundException("Usuário não encontrado com ID: " + id);
            }
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro inesperado", e);
        }
    }

    public void delete(Long id) {
        Optional<User> usuarioOptional = this.findById(id);
        if (usuarioOptional.isPresent()) {
            userRepository.deleteById(id);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuário não encontrado com ID: " + id);
        }
    }
}
