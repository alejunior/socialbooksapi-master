package com.socialbooks.handler;

import javax.servlet.http.HttpServletRequest;

import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.socialbooks.domain.DetalhesErro;
import com.socialbooks.services.exceptions.AutorExistenteException;
import com.socialbooks.services.exceptions.AutorNaoEncontradoException;
import com.socialbooks.services.exceptions.ComentarioNaoEncontradoException;
import com.socialbooks.services.exceptions.LivroExistenteException;
import com.socialbooks.services.exceptions.LivroNaoEncontradoException;
import com.socialbooks.services.exceptions.RecursoInacessivelException;

@ControllerAdvice
public class ResourceExceptionHandler {

	public DetalhesErro erro(int cod, String titulo) {
		DetalhesErro erro = new DetalhesErro();
		erro.setStatus(cod);
		erro.setTitulo(titulo);
		erro.setMensagemDesenvolvedor("http://erros.socialbooks.com/" + cod);
		erro.setTimestamp(System.currentTimeMillis());
		return erro;
	}

	@ExceptionHandler(LivroNaoEncontradoException.class)
	public ResponseEntity<DetalhesErro> handleLivroNaoEncontradoException(LivroNaoEncontradoException e,
			HttpServletRequest request) {
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(erro(404, "Livro não encontrado"));
	}

	@ExceptionHandler(ComentarioNaoEncontradoException.class)
	public ResponseEntity<DetalhesErro> handleComentarioNaoEncontradoException(ComentarioNaoEncontradoException e,
			HttpServletRequest request) {
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(erro(404, "Comentário não encontrado"));
	}

	@ExceptionHandler(AutorNaoEncontradoException.class)
	public ResponseEntity<DetalhesErro> handleAutorNaoEncontradoException(AutorNaoEncontradoException e,
			HttpServletRequest request) {
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(erro(404, "Autor não encontrado"));
	}

	@ExceptionHandler(AutorExistenteException.class)
	public ResponseEntity<DetalhesErro> handleAutorExistenteException(AutorExistenteException e,
			HttpServletRequest request) {
		return ResponseEntity.status(HttpStatus.CONFLICT).body(erro(409, "Conflito! O Autor já existe"));
	}

	@ExceptionHandler(LivroExistenteException.class)
	public ResponseEntity<DetalhesErro> handleLivroExistenteException(LivroExistenteException e,
			HttpServletRequest request) {
		return ResponseEntity.status(HttpStatus.CONFLICT).body(erro(409, "Conflito! O Livro já existe"));
	}

	@ExceptionHandler(RecursoInacessivelException.class)
	public ResponseEntity<DetalhesErro> handleRecursoInacessivelException(RecursoInacessivelException e,
			HttpServletRequest request) {
		return ResponseEntity.status(HttpStatus.FORBIDDEN).body(erro(403, "Acesso Negado"));
	}

	@ExceptionHandler(HttpRequestMethodNotSupportedException.class)
	public ResponseEntity<DetalhesErro> handlerHttpRequestMethodNotSupportedException(
			HttpRequestMethodNotSupportedException e, HttpServletRequest request) {
		return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED)
				.body(erro(405, "Há um erro na sua URL e ou verbo http, verifique!"));
	}

	@ExceptionHandler(InvalidDataAccessApiUsageException.class)
	public ResponseEntity<DetalhesErro> handlerInvalidDataAccessApiUsageException(InvalidDataAccessApiUsageException e,
			HttpServletRequest request) {
		return ResponseEntity.status(HttpStatus.BAD_REQUEST)
				.body(erro(400, "Arquivo não está contruído no padrão da API, verifique!"));
	}

	@ExceptionHandler(HttpMessageNotReadableException.class)
	public ResponseEntity<DetalhesErro> handlerHttpMessageNotReadableException(HttpMessageNotReadableException e,
			HttpServletRequest request) {
		return ResponseEntity.status(HttpStatus.BAD_REQUEST)
				.body(erro(400, "Arquivo não está contruído no padrão da API, verifique!"));
	}

}
