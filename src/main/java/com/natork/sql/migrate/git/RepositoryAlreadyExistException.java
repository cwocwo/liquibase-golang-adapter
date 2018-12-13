package com.natork.sql.migrate.git;

public class RepositoryAlreadyExistException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7697470598655872646L;

	public RepositoryAlreadyExistException(String message) {
		super(message);
	}
}
