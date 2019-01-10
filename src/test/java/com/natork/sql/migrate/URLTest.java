/**
 * 
 */
package com.natork.sql.migrate;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.natork.sql.migrate.liquibase.DataSource;
import com.natork.sql.migrate.liquibase.DatabaseMigrateException;
import com.natork.sql.migrate.liquibase.DbType;
import com.natork.sql.migrate.liquibase.Migrate;
import com.natork.sql.migrate.liquibase.MigrateService;

/**
 * @author caiweiwei
 *
 */
@Ignore
public class URLTest {
    
    @Test
    public void testClasspath() throws IOException {
        //URL.setURLStreamHandlerFactory(fac);
        URL location = new URL("classpath:/liquibase/dbchangelog-3.6.xsd");
        location.openConnection();
    }

}
