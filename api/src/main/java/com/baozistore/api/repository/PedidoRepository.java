package com.baozistore.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.baozistore.api.model.Pedido;

public interface PedidoRepository extends JpaRepository<Pedido, Long> {

}
