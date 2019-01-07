/**
 * 
 */
package com.natork.sql.migrate.liquibase;

import org.springframework.util.StringUtils;

/**
 * @author caiweiwei
 *
 */
public enum DbType {
	MYSQL {
	},
    
	POSTGRESQL {
    };
	public String getJdbcUrl(DataSource dataSource) {
	    String jdbcUrl = "jdbc:" + this.name().toLowerCase() + "://" + dataSource.getHost() + ":"
                + dataSource.getPort() + "/" + dataSource.getDatabase()
                + (StringUtils.isEmpty(dataSource.getParameters()) ? "" : "?" + dataSource.getParameters());
	    return jdbcUrl;
	}
}
