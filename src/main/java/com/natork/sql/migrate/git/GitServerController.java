/**
 * 
 */
package com.natork.sql.migrate.git;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author caiweiwei
 *
 */

@RestController
public class GitServerController {

    @Autowired
    private GitServer gitServer;

    @RequestMapping("/")
    public ModelAndView greeting(@RequestParam(value = "name", defaultValue = "World") String name) {
        Map<String, Object> data = new HashMap<>();
        data.put("city", "World!");
        return new ModelAndView("index", data);
    }

    @RequestMapping(value = "git-repos", method = RequestMethod.POST)
    public @ResponseBody GitRepo createRepo(@RequestBody GitRepo repo) {
        gitServer.createRepo(repo.getName());
        return repo;
    }

    @RequestMapping(value = "git-repos/{name}", method = RequestMethod.GET)
    public @ResponseBody GitRepo getRepo(@PathVariable("name") String name) {
        return gitServer.get(name);
    }
}
