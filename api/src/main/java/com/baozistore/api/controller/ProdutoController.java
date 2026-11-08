package com.baozistore.api.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baozistore.api.model.Produto;
import com.baozistore.api.repository.ProdutoRepository;

@RestController
@RequestMapping("/produtos")
public class ProdutoController {
	private ProdutoRepository repository;

	ProdutoController(ProdutoRepository repository) {
		this.repository = repository;
	}

	@PostMapping
	public ResponseEntity<Object> create(@RequestBody Produto produto) {
		if (produto.getNome() == null) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("O nome do produto é obrigratório.");
		}

		if (produto.getEstoque() == null) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("O estoque é obrigratório.");
		}

		if (produto.getPrice() == null) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("O price é obrigratório.");
		}

		Produto novoProduto = repository.save(produto);
		return ResponseEntity.status(HttpStatus.CREATED).body(novoProduto);
	}

	@GetMapping(path = "/{id}")
	public ResponseEntity<?> getProduto(@PathVariable Long id) {

		if (!repository.existsById(id)) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Produto não encontrado!");
		}

		return repository.findById(id).map(record -> ResponseEntity.ok().body(record))
				.orElse(ResponseEntity.notFound().build());
	}

	@GetMapping
	public List<Produto> getClientes() {
		return repository.findAll();
	}

	@DeleteMapping(path = "/{id}")
	public ResponseEntity<?> delete(@PathVariable Long id) {
	    if (!repository.existsById(id)) {
	        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Produto não encontrado!");
	    }
	    
	    repository.deleteById(id);
	    return ResponseEntity.ok().build();
	}
}
