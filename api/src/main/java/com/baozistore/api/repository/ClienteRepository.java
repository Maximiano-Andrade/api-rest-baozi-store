package com.baozistore.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.baozistore.api.model.Cliente;

public interface ClienteRepository extends JpaRepository<Cliente, Long>{

}
