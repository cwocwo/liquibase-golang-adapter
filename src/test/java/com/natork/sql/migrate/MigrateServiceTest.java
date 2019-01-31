/**
 * 
 */
package com.natork.sql.migrate;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.natork.sql.migrate.liquibase.DataSource;
import com.natork.sql.migrate.liquibase.DatabaseMigrateException;
import com.natork.sql.migrate.liquibase.DbType;
import com.natork.sql.migrate.liquibase.MigrateService;

/**
 * @author caiweiwei
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Ignore
public class MigrateServiceTest {
    
    @Autowired
    private MigrateService migrateService;
    /**
     * Test method for {@link com.natork.sql.migrate.liquibase.MigrateService#migrate(java.lang.String, java.lang.String, com.natork.sql.migrate.liquibase.DataSource)}.
     * @throws DatabaseMigrateException 
     */
    @Test
    public void testMigrate() throws DatabaseMigrateException {
        DataSource ds = new DataSource();
        ds.setDatabase("test");
        ds.setDbType(DbType.POSTGRESQL);
        ds.setHost("localhost");
        ds.setPassword("123456a?");
        ds.setPort((short)5432);
        ds.setUsername("test");
        
//        Migrate m = new Migrate();
//        m.setChangeLog("D:/git/main-exe");
//        m.setDataSource(ds);
        
        migrateService.migrate("main-exe", null, ds);
    }

}
