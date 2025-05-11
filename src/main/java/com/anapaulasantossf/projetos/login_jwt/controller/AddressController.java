package com.anapaulasantossf.projetos.login_jwt.controller;

import com.anapaulasantossf.projetos.login_jwt.model.Address;
import com.anapaulasantossf.projetos.login_jwt.service.AddressService;
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
    public List<Address> findByAll(){
        return addressService.findByAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Address> findById(@PathVariable Long id) {
        Optional<Address> address = addressService.findById(id);
        return address.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.noContent().build());
    }

    @PostMapping
    public ResponseEntity<Long> create(@RequestBody Address address) {
        Address returnAddress = addressService.create(address);
        return ResponseEntity.status(HttpStatus.CREATED).body(returnAddress.getId());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> update(@RequestBody Address address, @PathVariable Long id) {
        Address returnAddress = addressService.update(address, id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        addressService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
