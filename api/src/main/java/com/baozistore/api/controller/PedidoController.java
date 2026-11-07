package com.baozistore.api.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baozistore.api.model.Pedido;
import com.baozistore.api.repository.PedidoRepository;

@RestController
@RequestMapping("/pedidos")
public class PedidoController {
	private PedidoRepository repository;

	PedidoController(PedidoRepository repository) {
		this.repository = repository;
	}

	@PostMapping
	public Pedido create(@RequestBody Pedido pedido) {
		return repository.save(pedido);
	}

	@GetMapping(path = "/{id}")
	public ResponseEntity<?> getPedido(@PathVariable long id) {
		return repository.findById(id).map(record -> ResponseEntity.ok().body(record))
				.orElse(ResponseEntity.notFound().build());
	}

	@GetMapping
	public List<Pedido> getPedidos() {
		return repository.findAll();
	}

	@DeleteMapping(path = "/{id}")
	public ResponseEntity<?> delete(@PathVariable long id) {
		return repository.findById(id).map(record -> {
			repository.deleteById(id);
			return ResponseEntity.ok().build();
		}).orElse(ResponseEntity.notFound().build());
	}

}
