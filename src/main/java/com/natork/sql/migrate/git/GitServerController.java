/**
 * 
 */
package com.natork.sql.migrate.git;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author caiweiwei
 *
 */

@RestController(value="git/")
public class GitServerController {
	
	@Autowired
	private GitServer gitServer;

    @RequestMapping("/")
    public ModelAndView greeting(@RequestParam(value="name", defaultValue="World") String name) {
    	 Map<String, Object> data = new HashMap<>();
    	 data.put("city", "World!");
         return new ModelAndView("index", data);
    }
    
    @RequestMapping(value = "repositories", method = RequestMethod.POST)
    public ModelAndView createRepo(@RequestBody GitRepo repo) {
    	gitServer.createRepo(repo.getName());
    	Map<String, Object> data = new HashMap<>();
   	 	data.put("success", true);
    	return new ModelAndView("repositories", data);
    }
}
