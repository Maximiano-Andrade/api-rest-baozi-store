package com.baozistore.api.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;


@Entity
public class Pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Integer quantidade;
    private Long clienteid;
    private Long produtoid;

    public Pedido() {
    }

	public Pedido(Long id, Integer quantidade, Long clientid, Long produtoid) {
		this.id = id;
		this.quantidade = quantidade;
		this.clienteid = clientid;
		this.produtoid = produtoid;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(Integer quantidade) {
		this.quantidade = quantidade;
	}

	public Long getClienteid() {
		return clienteid;
	}

	public void setClienteid(Long clientid) {
		this.clienteid = clientid;
	}

	public Long getProdutoid() {
		return produtoid;
	}

	public void setProdutoid(Long produtoid) {
		this.produtoid = produtoid;
	}
    
    
  
}