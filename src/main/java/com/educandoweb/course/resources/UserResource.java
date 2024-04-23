package com.educandoweb.course.resources;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.educandoweb.course.entities.User;
import com.educandoweb.course.services.UserService;

@RestController // Indicador ao framework
// indica que se trata de um controlador Rest, voltado para o desenv. de apliações web Restful
@RequestMapping(value = "/users") // Anotação de nível de classe
//Define o prefixo de URL para todas as rotas de um controller.
public class UserResource {

	@Autowired // Cria dependências e acoplar dif. camadas da aplicação
	// Controle sobre como deve ser realizada a lig. entre os beans
	private UserService service;

	@GetMapping // Define uma rota que responde a req. HTTP GET
	public ResponseEntity<List<User>> findAll() {
		List<User> list = service.findAll();
		return ResponseEntity.ok().body(list);
	}

	@GetMapping(value = "/{id}")
	public ResponseEntity<User> findById(@PathVariable Long id) {
		// @PathVariable indica que o valor da variável virá de uma informação da rota
		User obj = service.findById(id);
		return ResponseEntity.ok().body(obj);
	}

	@PostMapping // Determina que o método aceitará requisições HTTP do tipo POST
	// Faz um pré processamento na compilação do compilador
	public ResponseEntity<User> insert(@RequestBody User obj) {
		// @RequestBody indica que o valor do objeto virá do corpo da requisição
		obj = service.insert(obj);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getId()).toUri();
		return ResponseEntity.created(uri).body(obj);
	}

	@DeleteMapping(value = "/{id}") // Determina que o método aceitará requisições HTTP do tipo DELETE.
	public ResponseEntity<Void> delete(@PathVariable Long id) {
		service.delete(id);
		return ResponseEntity.noContent().build();
	}

	@PutMapping(value = "/{id}") // Mapeia requisições HTTP PUT para atualizar um usuário existente
	public ResponseEntity<User> update(@PathVariable Long id, @RequestBody User obj) {
		obj = service.update(id, obj);
		return ResponseEntity.ok().body(obj);
	}
}