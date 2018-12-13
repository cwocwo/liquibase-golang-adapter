package com.natork.sql.migrate.git;

import java.io.IOException;
import java.util.Optional;

import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.lib.Repository;

class LazilyLoadedRepository {

    private final GitCloner gitCloner;
    private Optional<Repository> repository;

    LazilyLoadedRepository(String name, String repositoryUrl) {
        this.gitCloner = new GitCloner(name, repositoryUrl);
        this.repository = Optional.empty();
    }

    Repository get() {
        cloneRepository();
        return repository.get();
    }

    void close() {
        this.gitCloner.removeClone();
    }

    String getName() {
        return this.gitCloner.getRepositoryName();
    }

    void cloneRepository() {
        try {
            if (!repository.isPresent()) {
                this.repository = Optional.of(gitCloner.cloneRepositoryToTempFolder(true));
            }
        } catch (GitAPIException | IOException e) {
            throw new RuntimeException("Failed cloning repository [" + gitCloner.getRepositoryName() + ", " + gitCloner.getRepositoryUrl() + "].", e);
        }

    }
}