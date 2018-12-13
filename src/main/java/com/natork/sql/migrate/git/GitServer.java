/**
 * 
 */
package com.natork.sql.migrate.git;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.errors.RepositoryNotFoundException;
import org.eclipse.jgit.http.server.GitServlet;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.lib.StoredConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author caiweiwei
 * ref. https://www.programcreek.com/java-api-examples/?code=arquillian/smart-testing/smart-testing-master/functional-tests/git-rules/src/main/java/org/arquillian/smart/testing/rules/git/server/EmbeddedHttpGitServer.java#
 */

@Component
public class GitServer {
	
	private static Logger LOGGER = LoggerFactory.getLogger(GitServer.class);
	
	private static final String SUFFIX = ".git";
	
    private final Map<String, LazilyLoadedRepository> repositories = new HashMap<>();
    
    @Autowired
    private GitServerProperties configuration;
	
    private GitServlet gitServlet;
    
    @Value("${jgit.reporoot}")
	private String repoRoot;
    
    @PostConstruct
    public void init() {
    	this.loadRepos();
    	this.gitServlet = this.createGitServlet();
    }
	
	/**
	 * Create a new git repository
	 * @param name
	 */
	public void createRepo(String name) {
		File repoDir = new File(repoRoot + "/" + name);
		if(! repoDir.exists()) {
			repoDir.mkdirs();
			try {
				Git.init().setDirectory(repoDir).call();
				LOGGER.warn("Repo '{}' is creaated.", name);
			} catch (IllegalStateException | GitAPIException e) {
				LOGGER.error("Create repo {} error.", name, e);
			}
		} else {
			LOGGER.warn("Repo '{}' is already exists.", name);
			throw new RepositoryAlreadyExistException(name);
		}
	}
	
	/**
	 * Register an exist git repository
	 * @param name
	 */
	public void registerRepo(String name) {
		File repoDir = new File(configuration.getRepoRoot() + "/" + name);
		if(repoDir.exists()) {
			this.repositories.put(name, new LazilyLoadedRepository(name, repoDir.getAbsolutePath()));
			LOGGER.warn("Repo '{}' is registered.", name);
		} else {
			LOGGER.warn("Repo '{}' is not exists.", name);
			throw new RepositoryNotExistException(name);
		}
	}
	
	public GitServlet getGitServlet() {
		return gitServlet;
	}

	private GitServlet createGitServlet() {
        final GitServlet gitServlet = new GitServlet();
        gitServlet.setRepositoryResolver((req, name) -> {
            String trimmedName = name.endsWith(SUFFIX) ? name.substring(0, name.length() - SUFFIX.length()) : name;
            trimmedName = trimmedName.substring(trimmedName.lastIndexOf('/') + 1);
            if (repositories.containsKey(trimmedName)) {
                final LazilyLoadedRepository lazilyLoadedRepository = repositories.get(trimmedName);
                synchronized (gitServlet) {
                    lazilyLoadedRepository.cloneRepository();
                    final Repository repository = lazilyLoadedRepository.get();
                    enableInsecureReceiving(repository);
                    repository.incrementOpen();
                    return repository;
                }
            } else {
                throw new RepositoryNotFoundException("Repository " + name + "does not exist");
            }
        });
        gitServlet.addReceivePackFilter(new AfterReceivePackResetFilter(repositories.values()));
        LOGGER.info("Started serving local git repositories [" + this.repositories.values().stream().map(
	            LazilyLoadedRepository::getName).collect(Collectors.toList()) + "] ");
        return gitServlet;
    }
	
	private void loadRepos() {
		File repoRootDir = new File(repoRoot);
		if(!repoRootDir.exists()) {
			throw new RepositoryRootDirNotExistException("The git repo root directory: '" + repoRoot + "' not exists.");
		}
		if(!repoRootDir.isDirectory()) {
			throw new RepositoryRootDirNotExistException("The given git repo root directory: '" + repoRoot + "' is not a directory.");
		}
		for(File file : repoRootDir.listFiles()) {
			this.registerRepo(file);
		}
	}
	
	/**
	 * Register an exist git repository
	 * @param name
	 */
	private void registerRepo(File file) {
		if(!file.exists()) {
			LOGGER.info("Can't register repo: '{}' is a file.", file);
		}
		String name = file.getName();
		if(file.isDirectory()) {
			this.repositories.put(file.getName(), new LazilyLoadedRepository(name, file.getAbsolutePath()));
			LOGGER.info("Repo '{}' is registered.", name);
		} else {
			LOGGER.info("Can't register repo: '{}' is a file.", file);
		}
		
	}
	
	/**
     * To allow performing push operations from the cloned repository to remote (served by this server) let's
     * skip authorization for HTTP.
     */
    private void enableInsecureReceiving(Repository repository) {
        final StoredConfig config = repository.getConfig();
        config.setBoolean("http", null, "receivepack", true);
        try {
            config.save();
        } catch (IOException e) {
            throw new RuntimeException("Unable to save http.receivepack=true config", e);
        }
    }
}
