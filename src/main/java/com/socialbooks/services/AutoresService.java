package com.socialbooks.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.socialbooks.domain.Autor;
import com.socialbooks.repository.AutoresRepository;
import com.socialbooks.services.exceptions.AutorExistenteException;
import com.socialbooks.services.exceptions.AutorNaoEncontradoException;
import com.socialbooks.services.exceptions.RecursoInacessivelException;

@Service
public class AutoresService {

	@Autowired
	private AutoresRepository autoresRepository;

	public List<Autor> listar() {
		List<Autor> autores = autoresRepository.findAll();
		if (autores.isEmpty()) {
			throw new AutorNaoEncontradoException("Autor não encontrado");
		}
		return autores;
	}

	public Autor buscar(Long id) {
		if (id != null) {
			Autor autor = autoresRepository.findOne(id);
			if (autor == null) {
				throw new AutorNaoEncontradoException("Autor não encontrado");
			}
			return autor;
		} else {
			throw new AutorNaoEncontradoException("Autor não encontrado");
		}
	}

	public Autor salvar(Autor autor) {
		if (existeAutor(autor.getId())) {
			throw new AutorExistenteException("Autor já existente");
		} else {
			autoresRepository.save(autor);
			return autor;
		}
	}

	public boolean existeAutor(Long id) {
		try {
			buscar(id);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public Autor atualizar(Autor autor, Long id) {
		if (existeAutor(id)) {
			autor.setId(id);
			autoresRepository.save(autor);
			return autor;
		} else {
			throw new AutorNaoEncontradoException("Autor não encontrado");
		}
	}

	public void deletar(Long id) {
		try {
			autoresRepository.delete(id);
		} catch (Exception e) {
			throw new RecursoInacessivelException("O recurso está inacessível");
		}
	}

	
}
