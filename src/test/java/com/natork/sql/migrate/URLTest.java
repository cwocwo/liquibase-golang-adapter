/**
 * 
 */
package com.natork.sql.migrate;

import java.io.IOException;
import java.net.URL;

import org.junit.Ignore;
import org.junit.Test;

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
