/**
 * 
 */
package com.natork.sql.migrate.git;

import java.util.Arrays;

import org.eclipse.jgit.http.server.GitServlet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author caiweiwei
 *
 */
@Configuration
public class GitServerConfig {

    @Autowired
    private GitServer gitServer;

    @Bean
    ServletRegistrationBean<GitServlet> gitServletRegistration() {
        ServletRegistrationBean<GitServlet> srb = new ServletRegistrationBean<GitServlet>();
        srb.setServlet(gitServer.getGitServlet());
        srb.setUrlMappings(Arrays.asList("/git/*"));
        return srb;
    }
}
