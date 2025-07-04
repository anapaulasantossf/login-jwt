package com.anapaulasantossf.projetos.login_jwt.repository;

import com.anapaulasantossf.projetos.login_jwt.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    public Optional<User> findByEmail(String email);
}
