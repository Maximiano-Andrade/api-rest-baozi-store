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

import com.baozistore.api.model.Cliente;
import com.baozistore.api.repository.ClienteRepository;

@RestController
@RequestMapping("/clientes")
public class ClienteController {
	private ClienteRepository repository;

	ClienteController(ClienteRepository repository) {
		this.repository = repository;
	}

	@PostMapping
	public ResponseEntity<Object> create(@RequestBody Cliente cliente) {
		if (cliente.getNome() == null) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("O nome do usuario é obrigratório.");
		}

		Cliente novoCliente = repository.save(cliente);
		return ResponseEntity.status(HttpStatus.CREATED).body(novoCliente);
	}

	@GetMapping(path = "/{id}")
	public ResponseEntity<?> getCliente(@PathVariable Long id) {
		if(!repository.existsById(id)){
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Cliente não encontrado!");
		}
		
		return repository.findById(id).map(record -> ResponseEntity.ok().body(record))
				.orElse(ResponseEntity.notFound().build());
	}

	@GetMapping
	public List<Cliente> getClientes() {
		return repository.findAll();
	}

	@DeleteMapping(path = "/{id}")
	public ResponseEntity<?> delete(@PathVariable Long id) {
	    if (!repository.existsById(id)) {
	        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Cliente não encontrado!");
	    }
	    
	    repository.deleteById(id);
	    return ResponseEntity.ok().build();
	}

}
