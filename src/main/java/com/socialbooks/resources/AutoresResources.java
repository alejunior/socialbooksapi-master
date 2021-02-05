package com.socialbooks.resources;

import java.net.URI;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.socialbooks.domain.Autor;
import com.socialbooks.services.AutoresService;

@RestController
@RequestMapping("/autores")
public class AutoresResources {

	@Autowired
	private AutoresService autoresService;

	@CrossOrigin
	@RequestMapping(method = RequestMethod.GET, produces = { MediaType.APPLICATION_JSON_VALUE,
			MediaType.APPLICATION_XML_VALUE })
	public ResponseEntity<List<Autor>> listar() {
		List<Autor> autores = autoresService.listar();
		return ResponseEntity.ok().body(autores);
	}

	@CrossOrigin
	@RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
	public ResponseEntity<Autor> buscar(@PathVariable("id") Long id) {
		Autor autor = autoresService.buscar(id);
		return ResponseEntity.ok().body(autor);
	}

	@CrossOrigin
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<Autor> salvar(@Valid @RequestBody Autor autor) {
		Autor autorSalvo = autoresService.salvar(autor);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(autorSalvo.getId())
				.toUri();
		return ResponseEntity.created(uri).body(autorSalvo);
	}

	@CrossOrigin
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public ResponseEntity<Autor> atualizar(@Valid @PathVariable("id") Long id, @RequestBody Autor autor) {
		Autor autorAtualizado = autoresService.atualizar(autor, id);
		return ResponseEntity.ok().body(autorAtualizado);
	}

	@CrossOrigin
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Void> deletar(@PathVariable("id") Long id) {
		autoresService.deletar(id);
		return ResponseEntity.ok().build();
	}

}
