package com.anapaulasantossf.projetos.login_jwt.controller;

import com.anapaulasantossf.projetos.login_jwt.dto.AddressesResponseDTO;
import com.anapaulasantossf.projetos.login_jwt.dto.AddressesResquestDTO;
import com.anapaulasantossf.projetos.login_jwt.model.Address;
import com.anapaulasantossf.projetos.login_jwt.service.AddressService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RequestMapping("/users/{userId}/addresses")
@RestController
public class AddressesController {

    @Autowired
    AddressService addressService;

    @GetMapping
    public List<AddressesResponseDTO> findByAll(@PathVariable Long userId) {
        return addressService.findByAll(userId);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AddressesResponseDTO> findById(@PathVariable Long userId, @PathVariable Long id) {
        Optional<AddressesResponseDTO> address = addressService.findById(userId, id);
        return address.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.noContent().build());
    }

    @PostMapping
    public ResponseEntity<Long> create(@PathVariable Long userId, @Valid @RequestBody AddressesResquestDTO addressDTO) {
        AddressesResponseDTO updatedAddress = addressService.create(userId, addressDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(updatedAddress.getId());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Long> update(@PathVariable Long userId, @Valid @RequestBody AddressesResquestDTO addressDTO, @PathVariable Long id) {
        AddressesResponseDTO returnAddress = addressService.update(userId, addressDTO, id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(returnAddress.getId());
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long userId, @PathVariable Long id) {
        addressService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
