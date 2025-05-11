package com.anapaulasantossf.projetos.login_jwt.controller;

import com.anapaulasantossf.projetos.login_jwt.dto.AddressDTO;
import com.anapaulasantossf.projetos.login_jwt.model.Address;
import com.anapaulasantossf.projetos.login_jwt.service.AddressService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RequestMapping ("/address")
@RestController
public class AddressController {

    @Autowired
    AddressService addressService;

    @GetMapping
    public List<AddressDTO> findByAll(){
        return addressService.findByAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<AddressDTO> findById(@PathVariable Long id) {
        Optional<AddressDTO> address = addressService.findById(id);
        return address.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.noContent().build());
    }

    @PostMapping
    public ResponseEntity<Long> create(@Valid @RequestBody AddressDTO addressDTO) {
        AddressDTO updatedAddress = addressService.create(addressDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(updatedAddress.getId());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> update(@Valid @RequestBody AddressDTO addressDTO, @PathVariable Long id) {
        Address returnAddress = addressService.update(addressDTO, id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        addressService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
