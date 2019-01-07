package com.natork.sql.migrate.git;

import java.io.File;
import java.io.IOException;
import java.util.Optional;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.lib.Repository;

class LazilyLoadedRepository {
    private String repositoryUrl;
    private String repositoryName;
    private Optional<Repository> repository;

    LazilyLoadedRepository(String name, String repositoryUrl) {
        this.repositoryName = name;
        this.repositoryUrl = repositoryUrl;
        this.repository = Optional.empty();
    }

    Repository get() {
        openRepository();
        return repository.get();
    }

   

    String getName() {
        return this.repositoryName;
    }

    void openRepository() {
        try {
            if (!repository.isPresent()) {
                Repository repo = Git.open(new File(this.repositoryUrl)).getRepository();
                this.repository = Optional.of(repo);
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed openning repository [" + this.repositoryName + ", " + this.repositoryUrl + "].", e);
        }

    }
}