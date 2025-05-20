package com.anapaulasantossf.projetos.login_jwt.service;

import com.anapaulasantossf.projetos.login_jwt.dto.UserRequestDTO;
import com.anapaulasantossf.projetos.login_jwt.dto.UserResponseDTO;
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

    public Optional<UserResponseDTO> findById(Long id) {
        return Optional.ofNullable(userRepository.findById(id).map(this::toDTO)
                .orElseThrow(() -> new EntityNotFoundException("User not found with id " + id)));
    }

    public List<UserResponseDTO> findAll() {
        return userRepository.findAll().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public UserResponseDTO create(UserRequestDTO userDTO) {
        User user = toEntity(userDTO);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        User savedUser = userRepository.save(user);
        return toDTO(savedUser);
    }

    public UserResponseDTO update(Long id, UserRequestDTO userDTO) {
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
            return toDTO(userRepository.save(existingUser));

        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro inesperado", e);
        }
    }

    public void delete(Long id) {
        Optional<UserResponseDTO> usuarioOptional = this.findById(id);
        if (usuarioOptional.isPresent()) {
            userRepository.deleteById(id);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuário não encontrado com ID: " + id);
        }
    }

    public UserResponseDTO toDTO(User user) {
        UserResponseDTO dto = modelMapper.map(user, UserResponseDTO.class);
        dto.setPassword(null);
        return dto;
    }

    public User toEntity(UserRequestDTO userDTO) {
        return modelMapper.map(userDTO, User.class);
    }
}