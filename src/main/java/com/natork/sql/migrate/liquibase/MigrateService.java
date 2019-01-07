/**
 * 
 */
package com.natork.sql.migrate.liquibase;

import java.io.File;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import liquibase.Liquibase;
import liquibase.database.Database;
import liquibase.database.DatabaseFactory;
import liquibase.database.jvm.JdbcConnection;
import liquibase.exception.DatabaseException;
import liquibase.exception.LiquibaseException;
import liquibase.resource.FileSystemResourceAccessor;

/**
 * @author caiweiwei
 * https://www.programcreek.com/java-api-examples/?api=liquibase.resource.FileSystemResourceAccessor
 */
@Service
public class MigrateService {
	
	private static Logger LOGGER = LoggerFactory.getLogger(MigrateService.class);
	
	private static String CHANGELOG_DIR = "db" + File.separator + "changelog";
	
	@Value("${jgit.reporoot}")
	private String repoRoot;
	
	public void migrate(String changeLog, String contexts, DataSource dataSource) throws DatabaseMigrateException {
		this.checkDataSource(dataSource);
		String changeLogLocation = this.repoRoot + File.separator + changeLog + File.separator + CHANGELOG_DIR;
		this.checkChangelog(changeLogLocation);
		String jdbcUrl = dataSource.getDbType().getJdbcUrl(dataSource);
		LOGGER.info("The databse url is: '{}'.", jdbcUrl);
		java.sql.Connection c = null;
		try {
			String password = dataSource.getPassword() == null ? "" : dataSource.getPassword();
			LOGGER.info("The databse username: '{}', password: '{}'.", dataSource.getUsername(), password);
			c = DriverManager.getConnection(jdbcUrl, dataSource.getUsername(), password);
		} catch (SQLException e) {
			DatabaseMigrateException wrappedException = new DatabaseMigrateException(e);
			LOGGER.error("Connect databse error.", wrappedException);
			throw wrappedException;
		}
		try {
		    Database database = DatabaseFactory.getInstance().findCorrectDatabaseImplementation(new JdbcConnection(c));
		    Liquibase liquibase = new Liquibase(changeLogLocation + File.separator + "db.changelog-master.yaml", 
		            new FileSystemResourceAccessor(this.repoRoot + File.separator + changeLog), database);
		    liquibase.update(contexts);
		} catch (DatabaseException e) {
			DatabaseMigrateException wrappedException = new DatabaseMigrateException(e);
			LOGGER.error("Liquibase: databse error.", wrappedException);
			throw wrappedException;
		} catch (LiquibaseException e) {
			DatabaseMigrateException wrappedException = new DatabaseMigrateException(e);
			LOGGER.error("Liquibase: migrate error.", wrappedException);
			throw wrappedException;
		} finally {
		    if (c != null) {
		        try {
		            c.close();
		        } catch (SQLException e) {
		        	LOGGER.warn("Close database connect error.", e);
		        }
		    }
		}
	}
	
	private void checkChangelog(String changeLogLocation) throws DatabaseMigrateException {
		File file = new File(changeLogLocation);
		if(! file.exists()) {
			LOGGER.error("Changelog file '{}' not exists.", changeLogLocation);
			throw new DatabaseMigrateException("Changelog file '" + changeLogLocation + "' not exists.");
		}
		if(! file.isDirectory()) {
			LOGGER.error("Changelog file '{}' is a file, but require a directory.", changeLogLocation);
			throw new DatabaseMigrateException("Changelog file '" + changeLogLocation + "' is a file, but require a directory.");
		}
	}
	
	private void checkDataSource(DataSource dataSource) throws DatabaseMigrateException {
		String errMessage = "";
		
		if(dataSource.getDbType() == null) {
			errMessage += "DbType is required.\n";
		}
		if(StringUtils.isEmpty(dataSource.getHost())) {
			errMessage += "Host is required.\n";
		}
		if(dataSource.getPort() == null) {
			errMessage += "Port is required.\n";
		}
		if(StringUtils.isEmpty(dataSource.getDatabase())) {
			errMessage += "Database is required.\n";
		}
		if(StringUtils.isEmpty(dataSource.getUsername())) {
			errMessage += "Username is required.\n";
		}
		if(StringUtils.isEmpty(dataSource.getPassword())) {
			LOGGER.warn("The password of dataSource is empty!");
		}
		if(!StringUtils.isEmpty(errMessage)) {
			LOGGER.error(errMessage);
			throw new DatabaseMigrateException(errMessage);
		}
	}
}
