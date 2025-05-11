package com.anapaulasantossf.projetos.login_jwt.service;

import com.anapaulasantossf.projetos.login_jwt.dto.AddressDTO;
import com.anapaulasantossf.projetos.login_jwt.model.Address;
import com.anapaulasantossf.projetos.login_jwt.repository.AddressRepository;
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
    ModelMapper modelMapper;

    public List<AddressDTO> findByAll() {
        return addressRepository.findAll().stream().map(this::toDTO).collect(Collectors.toList());
    }

    public Optional<AddressDTO> findById(Long id) {
        return Optional.ofNullable(addressRepository.findById(id)
                .map(this::toDTO)
                .orElseThrow(() -> new EntityNotFoundException("Endereço não encontrado " + id)));
    }

    public AddressDTO create(AddressDTO addressDTO) {
        return toDTO(addressRepository.save(toEntity(addressDTO)));
    }

    public Address update(AddressDTO addressDTO, Long id) {
        Address exitsAddress = addressRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Endereço não encontrado " + id));
        exitsAddress = this.toEntity(addressDTO);
        return addressRepository.saveAndFlush(exitsAddress);
    }

    public void delete(Long id) {
        Optional<Address> returnAddress = addressRepository.findById(id);
        if (returnAddress.isPresent()) {
            addressRepository.deleteById(id);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Endereço não encontrado" + id);
        }
    }

    public AddressDTO toDTO(Address address) {
        return modelMapper.map(address, AddressDTO.class);
    }

    public Address toEntity(AddressDTO addressDTO) {
        return modelMapper.map(addressDTO, Address.class);
    }
}
