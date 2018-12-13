/**
 * 
 */
package com.natork.sql.migrate.liquibase;

/**
 * @author caiweiwei
 *
 */
public enum DbType {
	MYSQL {

		@Override
		String getJdbcUrl(DataSource dataSource) {
			return "jdbc:mysql://" + dataSource.getHost() + ":"
					+ dataSource.getPort() + "/" + dataSource.getDatabase()
					+ "?" + dataSource.getParameters() == null ? "" : dataSource.getParameters();
		}
		
	};
	
	abstract String getJdbcUrl(DataSource dataSource);
}
