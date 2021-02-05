package com.socialbooks.services.exceptions;

public class RecursoInacessivelException extends RuntimeException{

	private static final long serialVersionUID = 4661136496177602010L;
	
	public RecursoInacessivelException(String mensagem) {
		super(mensagem);
	}
	
	public RecursoInacessivelException(String mensagem, Throwable causa) {
		super(mensagem, causa);
	}
}
