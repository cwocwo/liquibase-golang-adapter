/**
 * 
 */
package com.natork.sql.migrate.liquibase;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author caiweiwei
 *
 */
@RestController
public class MigrateController {
    
    @Autowired
    private MigrateService migrateService;
    
    @RequestMapping(value = "migrates", method = RequestMethod.POST)
    public @ResponseBody Object createRepo(@RequestBody Migrate migrate) {
        Map<String, Object> rs = new HashMap<>();
        try {
            migrateService.migrate(migrate.getChangeLog(), migrate.getContexts(), migrate.getDataSource());
            rs.put("success", true);
            rs.put("message", "the migrate was successfully executed.");
        } catch (DatabaseMigrateException e) {
            rs.put("success", false);
            rs.put("message", e.getMessage());
        }
        return rs;
    }
}
