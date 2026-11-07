package com.baozistore.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.baozistore.api.model.Produto;

public interface ProdutoRepository extends JpaRepository<Produto, Long> {

}
