package com.anapaulasantossf.projetos.login_jwt.service;

import com.anapaulasantossf.projetos.login_jwt.model.Address;
import com.anapaulasantossf.projetos.login_jwt.repository.AddressRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class AddressService {

    @Autowired
    AddressRepository addressRepository;

    public List<Address> findByAll() {
        return addressRepository.findAll();
    }

    public Optional<Address> findById(Long id) {
        Optional<Address> address = addressRepository.findById(id);
        return address;
    }

    public Address create(Address address) {
        return addressRepository.save(address);
    }

    public Address update(Address address, Long id) {

        try {
            Optional<Address> returnAddress = addressRepository.findById(id);
            if (returnAddress.isPresent()) {
                return addressRepository.saveAndFlush(address);
            } else {
                throw new EntityNotFoundException("Endereço não encontrado com ID: " + id);
            }
        } catch (Exception exception) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro inesperado", exception);
        }
    }

    public void delete(Long id) {
        Optional<Address> returnAddress = this.findById(id);
        if (returnAddress.isPresent()) {
            addressRepository.deleteById(id);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Endereço não encontrado" + id);
        }
    }

}
