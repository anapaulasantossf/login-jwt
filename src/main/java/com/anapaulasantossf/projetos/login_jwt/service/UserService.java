package com.anapaulasantossf.projetos.login_jwt.service;

import com.anapaulasantossf.projetos.login_jwt.dto.UserDTO;
import com.anapaulasantossf.projetos.login_jwt.model.User;
import com.anapaulasantossf.projetos.login_jwt.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    private ModelMapper modelMapper;

    public Optional<UserDTO> findById(Long id) {
        return Optional.ofNullable(userRepository.findById(id).map(this::toDTO)
                .orElseThrow(() -> new EntityNotFoundException("User not found with id " + id)));
    }

    public List<UserDTO> findAll() {
        return userRepository.findAll().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public UserDTO create(UserDTO userDTO) {
        User user = toEntity(userDTO);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        User savedUser = userRepository.save(user);
        return toDTO(savedUser);
    }

    public UserDTO update(Long id, UserDTO userDTO) {
        try {
            User existingUser = userRepository.findById(id)
                    .orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado com ID " + id));

            existingUser.setEmail(userDTO.getEmail());
            existingUser.setFirstName(userDTO.getFirstName());
            existingUser.setLastName(userDTO.getLastName());
            existingUser.setPhoneNumber(userDTO.getPhoneNumber());

            if (userDTO.getPassword() != null && !userDTO.getPassword().isBlank()) {
                existingUser.setPassword(passwordEncoder.encode(userDTO.getPassword()));
            }
            User updatedUser = userRepository.save(existingUser);
            return toDTO(updatedUser);

        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro inesperado", e);
        }
    }

    public void delete(Long id) {
        Optional<UserDTO> usuarioOptional = this.findById(id);
        if (usuarioOptional.isPresent()) {
            userRepository.deleteById(id);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuário não encontrado com ID: " + id);
        }
    }

    public UserDTO toDTO(User user) {
        UserDTO dto = modelMapper.map(user, UserDTO.class);
        dto.setPassword(null);
        return dto;
    }

    public User toEntity(UserDTO userDTO) {
        return modelMapper.map(userDTO, User.class);
    }
}