package com.socialbooks.services.exceptions;

public class AutorNaoEncontradoException extends RuntimeException{

	private static final long serialVersionUID = 7730399617426177322L;
	
	public AutorNaoEncontradoException(String mensagem) {
		super(mensagem);
	}

	public AutorNaoEncontradoException(String mensagem, Throwable causa) {
		super(mensagem, causa);
	}
}
