package com.anapaulasantossf.projetos.login_jwt.controller;

import com.anapaulasantossf.projetos.login_jwt.model.User;
import com.anapaulasantossf.projetos.login_jwt.repository.UserRepository;
import com.anapaulasantossf.projetos.login_jwt.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping
    public List<User> getAll(){
        return userService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getFindById(@PathVariable Long id){

        Optional<User> user = userService.findById(id);
        return user.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.noContent().build());
    }

    @PostMapping
    public ResponseEntity<Long> create(@RequestBody User user) {

        User returnUser = userService.create(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(returnUser.getId());
    }

    @PutMapping("/{id}")
    public ResponseEntity<User> update(@PathVariable Long id, @RequestBody User user) {
        User usuario = userService.update(id, user);
        return ResponseEntity.ok(usuario);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        userService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
