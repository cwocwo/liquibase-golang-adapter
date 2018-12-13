package com.natork.sql.migrate.git;

@SuppressWarnings("serial")
public class RepositoryNotExistException extends RuntimeException {

	public RepositoryNotExistException(String message) {
		super(message);
	}
}
