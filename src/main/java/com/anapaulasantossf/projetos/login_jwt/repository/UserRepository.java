package com.anapaulasantossf.projetos.login_jwt.repository;

import com.anapaulasantossf.projetos.login_jwt.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    public Optional<User> findByEmail(String email);
}
