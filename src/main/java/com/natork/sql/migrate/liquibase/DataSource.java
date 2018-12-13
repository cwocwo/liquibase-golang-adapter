/**
 * 
 */
package com.natork.sql.migrate.liquibase;

/**
 * @author caiweiwei
 *
 */
public class DataSource {
	private DbType dbType;
	private String host;
	private Short port;
	private String database;
	private String parameters;
	private String username;
	private String password;
	
	public DbType getDbType() {
		return dbType;
	}
	public void setDbType(DbType dbType) {
		this.dbType = dbType;
	}
	public String getHost() {
		return host;
	}
	public void setHost(String host) {
		this.host = host;
	}
	public Short getPort() {
		return port;
	}
	public void setPort(Short port) {
		this.port = port;
	}
	public String getDatabase() {
		return database;
	}
	public void setDatabase(String database) {
		this.database = database;
	}
	public String getParameters() {
		return parameters;
	}
	public void setParameters(String parameters) {
		this.parameters = parameters;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
}
