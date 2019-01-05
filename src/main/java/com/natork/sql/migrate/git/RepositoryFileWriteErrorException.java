package com.natork.sql.migrate.git;

public class RepositoryFileWriteErrorException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7697470598655872646L;

	public RepositoryFileWriteErrorException(String message) {
		super(message);
	}

    public RepositoryFileWriteErrorException(String message, Throwable cause) {
        super(message, cause);
    }
}
