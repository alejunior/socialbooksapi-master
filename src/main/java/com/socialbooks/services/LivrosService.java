package com.socialbooks.services;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.socialbooks.domain.Comentario;
import com.socialbooks.domain.Livro;
import com.socialbooks.repository.ComentariosRepository;
import com.socialbooks.repository.LivrosRepository;
import com.socialbooks.services.exceptions.AutorNaoEncontradoException;
import com.socialbooks.services.exceptions.ComentarioNaoEncontradoException;
import com.socialbooks.services.exceptions.LivroExistenteException;
import com.socialbooks.services.exceptions.LivroNaoEncontradoException;
import com.socialbooks.services.exceptions.RecursoInacessivelException;

@Service
public class LivrosService {

	@Autowired
	private LivrosRepository livrosRepository;

	@Autowired
	private ComentariosRepository comentariosRepository;

	public List<Livro> listar() {
		List<Livro> livros = livrosRepository.findAll();
		if (livros.isEmpty()) {
			throw new LivroNaoEncontradoException("O Livro não pode ser encontrado.");
		}
		return livros;
	}

	public Livro buscar(Long id) {
		if (id != null) {
			Livro livro = livrosRepository.findOne(id);
			if (livro == null) {
				throw new LivroNaoEncontradoException("O Livro não pode ser encontrado.");
			}
			return livro;
		} else {
			throw new LivroNaoEncontradoException("O Livro não pode ser encontrado.");
		}
	}

	public Livro salvar(Livro livro) {
		if (existeLivro(livro.getId())) {
			throw new LivroExistenteException("Livro já existente");
		}
		try {
		return livrosRepository.save(livro);
		}catch (DataIntegrityViolationException e) {
			throw new AutorNaoEncontradoException("Autor não encontrado");
		}
	}

	public void deletar(Long id) {
		try {
			livrosRepository.delete(id);
		} catch (Exception e) {
			throw new RecursoInacessivelException("O recurso está inacessível");
		}
	}

	public Livro atualizar(Livro livro, Long id) {
		if (existeLivro(id)) {
			livro.setId(id);
			return livrosRepository.save(livro);
		} else {
			throw new LivroNaoEncontradoException("Livro não encontrado");
		}
	}

	private boolean existeLivro(Long id) {
		try {
			buscar(id);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public Comentario salvarComentario(Long id, Comentario comentario) { // POST
		if (existeLivro(id)) {
			comentario.setLivro(buscar(id));
			comentario.setData(new Date());
			return comentariosRepository.save(comentario);
		} else {
			throw new LivroNaoEncontradoException("O Livro não pode ser encontrado.");
		}
	}

	public List<Comentario> listarComentarios(Long id) { // GET ALL COMENTARIOS
		Livro livro = buscar(id);
		List<Comentario> listaComentario = livro.getComentarios(); // lista de comentarios
		if (listaComentario.isEmpty()) {
			throw new ComentarioNaoEncontradoException("O comentario não pode ser encontrado.");
		}
		return listaComentario; // lista de comentarios
	}

	public Comentario buscarComentario(Long id, Long comentarioId) { // GET
		List<Comentario> listaComentarios = listarComentarios(id);
		if (listaComentarios.isEmpty()) {
			throw new ComentarioNaoEncontradoException("O comentario não pode ser encontrado.");
		}
		for (int i = 0; i < listaComentarios.size(); i++) {
			if (listaComentarios.get(i).getId().equals(comentarioId)) {
				return listaComentarios.get(i);
			}
		}
		return null;
	}

	public Comentario atualizarComentario(Comentario comentario, Long id, Long comentarioId) {
		Comentario comentarioEncontrado = buscarComentario(id, comentarioId);
		comentarioEncontrado.setData(new Date());
		comentarioEncontrado.setTexto(comentario.getTexto());
		return comentariosRepository.save(comentarioEncontrado);
	}

	public void deletarComentario(Long id, Long comentarioId) {
		try {
			buscar(id); // verificar se existe o livro referente ao comentario.
			comentariosRepository.delete(comentarioId);
		} catch (EmptyResultDataAccessException e) {
			throw new ComentarioNaoEncontradoException("O comentario não pode ser encontrado.");
		}
	}

	public void deletarListaComentarios(Long id) {
		List<Comentario> listaComentarios = listarComentarios(id);
		for (int i = 0; i < listaComentarios.size(); i++) {
			comentariosRepository.delete(listaComentarios.get(i).getId());
		}
	}

}
