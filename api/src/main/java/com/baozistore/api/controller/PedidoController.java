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

	PedidoController(PedidoRepository pedidoRepository, ClienteRepository clienteRepository,
			ProdutoRepository produtoRepository) {
		this.pedidoRepository = pedidoRepository;
		this.clienteRepository = clienteRepository;
		this.produtoRepository = produtoRepository;
	}

	@PostMapping
	public ResponseEntity<Object> create(@RequestBody Pedido pedido) {
		// 1. Valida se o cliente foi informado e se existe no banco
		if (pedido.getCliente() == null || pedido.getCliente().getId() == null) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("O ID do cliente é obrigatório.");
		}
		if (!clienteRepository.existsById(pedido.getCliente().getId())) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Cliente não encontrado com o ID informado.");
		}

		// 2. Valida se o produto foi informado e se existe no banco
		if (pedido.getProduto() == null || pedido.getProduto().getId() == null) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("O ID do produto é obrigatório.");
		}
		if (!produtoRepository.existsById(pedido.getProduto().getId())) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Produto não encontrado com o ID informado.");
		}

		// 3. Valida a quantidade
		if (pedido.getQuantidade() == null || pedido.getQuantidade() <= 0) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("A quantidade deve ser maior que zero.");
		}

		// Se passar por todas as validações, salva o pedido
		Pedido novoPedido = pedidoRepository.save(pedido);
		return ResponseEntity.status(HttpStatus.CREATED).body(novoPedido);
	}

	@GetMapping("/{id}")
	public ResponseEntity<Pedido> buscarPedidoPorId(@PathVariable Long id) {
		Optional<Pedido> pedido = pedidoRepository.findById(id);
		return pedido.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
	}

	@GetMapping
	public List<Pedido> listarPedidos() {
		return pedidoRepository.findAll();
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deletarPedido(@PathVariable Long id) {
		if (pedidoRepository.existsById(id)) {
			pedidoRepository.deleteById(id);
			return ResponseEntity.noContent().build();
		}
		return ResponseEntity.notFound().build();
	}

}
