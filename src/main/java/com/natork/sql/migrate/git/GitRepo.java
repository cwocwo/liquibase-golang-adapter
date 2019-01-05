/**
 * 
 */
package com.natork.sql.migrate.git;

/**
 * @author caiweiwei
 *
 */
public class GitRepo {
    private String name;

    public GitRepo() {

    }

    public GitRepo(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
