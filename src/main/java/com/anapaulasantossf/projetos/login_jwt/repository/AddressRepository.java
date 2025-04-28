package com.anapaulasantossf.projetos.login_jwt.repository;

import com.anapaulasantossf.projetos.login_jwt.model.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {


}
