package com.socialbooks.services.exceptions;

public class ComentarioNaoEncontradoException extends RuntimeException {
	
	private static final long serialVersionUID = 3185639654951560794L;

	public ComentarioNaoEncontradoException(String mensagem) {
		super(mensagem);
	}
	
	public ComentarioNaoEncontradoException(String mensagem, Throwable causa) {
		super(mensagem, causa);
	}
}
