/**
 * 
 */
package com.natork.sql.migrate.git;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author caiweiwei
 *
 */

@Component
public class GitServerProperties {
	
	@Value("${jgit.reporoot}")
	private String repoRoot;

	public String getRepoRoot() {
		return repoRoot;
	}

	public void setRepoRoot(String repoRoot) {
		this.repoRoot = repoRoot;
	}
	
}
