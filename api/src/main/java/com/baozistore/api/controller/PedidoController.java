package com.baozistore.api.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baozistore.api.model.Pedido;
import com.baozistore.api.repository.ClienteRepository;
import com.baozistore.api.repository.PedidoRepository;
import com.baozistore.api.repository.ProdutoRepository;

@RestController
@RequestMapping("/pedidos")
public class PedidoController {
	private PedidoRepository pedidoRepository;
	private ClienteRepository clienteRepository;
	private ProdutoRepository produtoRepository;

	PedidoController(PedidoRepository pedidoRepository, ClienteRepository clienteRepository, ProdutoRepository produtoRepository) {
		this.pedidoRepository = pedidoRepository;
		this.clienteRepository = clienteRepository;
		this.produtoRepository = produtoRepository;
	}

	@PostMapping
	public ResponseEntity<Object> create(@RequestBody Pedido pedido) {
		
		if (pedido.getCliente() == null || pedido.getCliente().getId() == null) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("O ID do cliente é obrigatório.");
		}
		if (!clienteRepository.existsById(pedido.getCliente().getId())) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Cliente não encontrado com o ID informado.");
		}
		
		if (pedido.getProduto() == null || pedido.getProduto().getId() == null) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("O ID do produto é obrigatório.");
		}
		if (!produtoRepository.existsById(pedido.getProduto().getId())) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Produto não encontrado com o ID informado.");
		}

		if (pedido.getQuantidade() == null || pedido.getQuantidade() <= 0) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("A quantidade deve ser maior que zero.");
		}

		Pedido novoPedido = pedidoRepository.save(pedido);
		return ResponseEntity.status(HttpStatus.CREATED).body(novoPedido);
	}

	@GetMapping(path = "/{id}")
	public ResponseEntity<?> getPedido(@PathVariable Long id) {
		Optional<Pedido> pedido = pedidoRepository.findById(id);

		if (pedido.isPresent()) {
			return ResponseEntity.ok(pedido.get());
		}

		return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Pedidp não encontrado!");
	}

	@GetMapping
	public List<Pedido> listar() {
		return pedidoRepository.findAll();
	}

	@DeleteMapping(path = "/{id}")
	public ResponseEntity<?> delete(@PathVariable Long id) {
	    if (!pedidoRepository.existsById(id)) {
	        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Pedido não encontrado!");
	    }
	    
	    pedidoRepository.deleteById(id);
	    return ResponseEntity.ok().build();
	}

}
