/**
 * 
 */
package com.natork.sql.migrate.liquibase;

/**
 * @author caiweiwei
 *
 */
@SuppressWarnings("serial")
public class DatabaseMigrateException extends RuntimeException {

	public DatabaseMigrateException(String message, Throwable cause) {
		super(message, cause);
	}

	public DatabaseMigrateException(String message) {
		super(message);
	}

	public DatabaseMigrateException(Throwable cause) {
		super(cause);
	}
	
}
