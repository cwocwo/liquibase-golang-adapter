/**
 * 
 */
package com.natork.sql.migrate;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.junit.Test;

/**
 * @author caiweiwei
 *
 */
public class JdbcConnectionTest {

    @Test
    public void test() {
        String jdbcUrl = "jdbc:postgresql://localhost:5432/test";
        try {
            Connection c = DriverManager.getConnection(jdbcUrl, "test", "123456a?");
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
    }

}
