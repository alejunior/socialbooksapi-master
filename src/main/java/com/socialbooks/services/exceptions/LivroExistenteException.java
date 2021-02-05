package com.socialbooks.services.exceptions;

public class LivroExistenteException extends RuntimeException{

	private static final long serialVersionUID = -1201644862363770134L;

	public LivroExistenteException(String mensagem) {
		super(mensagem);
	}
	
	public LivroExistenteException(String mensagem, Throwable causa) {
		super(mensagem, causa);
	}
}
