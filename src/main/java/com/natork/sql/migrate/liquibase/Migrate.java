/**
 * 
 */
package com.natork.sql.migrate.liquibase;

/**
 * @author caiweiwei
 *
 */
public class Migrate {
    private String changeLog;
    private String contexts;
    private DataSource dataSource;
    public String getChangeLog() {
        return changeLog;
    }
    public void setChangeLog(String changeLog) {
        this.changeLog = changeLog;
    }
    public String getContexts() {
        return contexts;
    }
    public void setContexts(String contexts) {
        this.contexts = contexts;
    }
    public DataSource getDataSource() {
        return dataSource;
    }
    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }
    
    
}
