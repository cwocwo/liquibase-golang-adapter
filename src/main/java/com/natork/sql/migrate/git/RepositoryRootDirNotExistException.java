package com.natork.sql.migrate.git;

@SuppressWarnings("serial")
public class RepositoryRootDirNotExistException extends RuntimeException {

	public RepositoryRootDirNotExistException(String message) {
		super(message);
	}
	
}
