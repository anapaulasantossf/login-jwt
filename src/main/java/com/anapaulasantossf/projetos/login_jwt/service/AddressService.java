package com.anapaulasantossf.projetos.login_jwt.service;

import com.anapaulasantossf.projetos.login_jwt.dto.AddressesResponseDTO;
import com.anapaulasantossf.projetos.login_jwt.dto.AddressesResquestDTO;
import com.anapaulasantossf.projetos.login_jwt.dto.UserResponseDTO;
import com.anapaulasantossf.projetos.login_jwt.model.Address;
import com.anapaulasantossf.projetos.login_jwt.model.User;
import com.anapaulasantossf.projetos.login_jwt.repository.AddressRepository;
import com.anapaulasantossf.projetos.login_jwt.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AddressService {

    @Autowired
    AddressRepository addressRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    ModelMapper modelMapper;

    public List<AddressesResponseDTO> findByAll(Long userId) {
        return addressRepository.findAllByUserId(userId).stream().map(this::toDTO).collect(Collectors.toList());
    }

    public Optional<AddressesResponseDTO> findById(Long userId, Long id) {
        return Optional.ofNullable(addressRepository.findByIdAndUserId(id, userId)
                .map(this::toDTO)
                .orElseThrow(() -> new EntityNotFoundException("Endereço não encontrado " + id)));
    }

    public AddressesResponseDTO create(Long userId, AddressesResquestDTO addressDTO) {
        Optional<User> userDTO = userRepository.findById(userId);
        addressDTO.setUser(userDTO.get());
        return toDTO(addressRepository.save(toEntity(addressDTO)));
    }

    public AddressesResponseDTO update(Long userId, AddressesResquestDTO addressDTO, Long id) {
        Optional<User> userDTO = userRepository.findById(userId);
        Address exitsAddress = addressRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Endereço não encontrado " + id));
        addressDTO.setUser(userDTO.get());
        exitsAddress = this.toEntity(addressDTO);
        return toDTO(addressRepository.saveAndFlush(exitsAddress));
    }

    public void delete(Long id) {
        Optional<Address> returnAddress = addressRepository.findById(id);
        if (returnAddress.isPresent()) {
            addressRepository.deleteById(id);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Endereço não encontrado" + id);
        }
    }

    public AddressesResponseDTO toDTO(Address address) {
        return modelMapper.map(address, AddressesResponseDTO.class);
    }

    public Address toEntity(AddressesResquestDTO addressDTO) {
        return modelMapper.map(addressDTO, Address.class);
    }
}
