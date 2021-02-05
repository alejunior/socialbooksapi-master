package com.socialbooks.resources;

import java.net.URI;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.socialbooks.domain.Comentario;
import com.socialbooks.domain.Livro;
import com.socialbooks.services.LivrosService;

@RestController
@RequestMapping(value = "/livros")
public class LivrosResources {

	@Autowired
	private LivrosService livrosService;

	@CrossOrigin
	@RequestMapping(method = RequestMethod.GET, produces = { MediaType.APPLICATION_JSON_VALUE,
			MediaType.APPLICATION_XML_VALUE })
	public ResponseEntity<List<Livro>> listar() {
		List<Livro> livros = livrosService.listar();
		return ResponseEntity.ok().body(livros);
	}

	@CrossOrigin
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<Livro> salvar(@Valid @RequestBody Livro livro) {
		Livro livroSalvo = livrosService.salvar(livro);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(livroSalvo.getId())
				.toUri();
		return ResponseEntity.created(uri).body(livroSalvo);
	}

	@CrossOrigin
	@RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = { MediaType.APPLICATION_JSON_VALUE,
			MediaType.APPLICATION_XML_VALUE })
	public ResponseEntity<Livro> buscar(@PathVariable("id") Long id) {
		Livro livro = livrosService.buscar(id);
		CacheControl cacheControl = CacheControl.maxAge(60, TimeUnit.SECONDS); // VERIFICAR
		return ResponseEntity.status(HttpStatus.OK).cacheControl(cacheControl).body(livro);
	}

	@CrossOrigin
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Void> deletar(@PathVariable("id") Long id) {
		livrosService.deletar(id);
		return ResponseEntity.ok().build();
	}

	@CrossOrigin
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public ResponseEntity<Livro> atualizar(@Valid @RequestBody Livro livro, @PathVariable("id") Long id) {
		Livro livroAlterado = livrosService.atualizar(livro, id);
		return ResponseEntity.ok(livroAlterado);
	}

	// COMENTÁRIOS --------------- //

	@CrossOrigin
	@RequestMapping(value = "/{id}/comentarios", method = RequestMethod.POST)
	public ResponseEntity<Comentario> adicionarComentario(@Valid @PathVariable("id") Long id,
			@RequestBody Comentario comentario) {

		// Capturar o USER autenticado na API e uncluir no usuário do comentário
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		comentario.setUsuario(auth.getName());

		Comentario livroComComentario = livrosService.salvarComentario(id, comentario);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				.buildAndExpand(livroComComentario.getId()).toUri();
		return ResponseEntity.created(uri).body(livroComComentario);
	}

	@CrossOrigin
	@RequestMapping(value = "/{id}/comentarios", method = RequestMethod.GET, produces = {
			MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
	public ResponseEntity<List<Comentario>> listarComentarios(@PathVariable("id") Long id) {
		List<Comentario> comentario = livrosService.listarComentarios(id);
		return ResponseEntity.ok().body(comentario);
	}

	@CrossOrigin
	@RequestMapping(value = "/{id}/comentarios/{comentarioId}", method = RequestMethod.GET, produces = {
			MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
	public ResponseEntity<Comentario> buscarComentario(@PathVariable("id") Long id,
			@PathVariable("comentarioId") Long comentarioId) {
		Comentario comentario = livrosService.buscarComentario(id, comentarioId);
		return ResponseEntity.ok().body(comentario);
	}

	@CrossOrigin
	@RequestMapping(value = "/{id}/comentarios/{comentarioId}", method = RequestMethod.PUT)
	public ResponseEntity<Comentario> atualizarComentario(@Valid @RequestBody Comentario comentario,
			@PathVariable("id") Long id, @PathVariable("comentarioId") Long comentarioId) {
		Comentario comentarioAlterado = livrosService.atualizarComentario(comentario, id, comentarioId);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().build().toUri();
		return ResponseEntity.created(uri).body(comentarioAlterado);
	}

	@CrossOrigin
	@RequestMapping(value = "/{id}/comentarios/{comentarioId}", method = RequestMethod.DELETE)
	public ResponseEntity<Void> deletarComentario(@PathVariable("id") Long id,
			@PathVariable("comentarioId") Long comentarioId) {
		livrosService.deletarComentario(id, comentarioId);
		return ResponseEntity.ok().build();
	}

	@CrossOrigin
	@RequestMapping(value = "/{id}/comentarios/todos", method = RequestMethod.DELETE)
	public ResponseEntity<Void> deletarListaComentarios(@PathVariable("id") Long id) {
		livrosService.deletarListaComentarios(id);
		return ResponseEntity.ok().build();
	}

}