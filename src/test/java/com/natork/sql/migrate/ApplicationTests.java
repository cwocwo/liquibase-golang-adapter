package com.natork.sql.migrate;

import org.junit.Ignore;
import org.junit.Test;

import com.natork.sql.migrate.git.GitCloner;

@Ignore
public class ApplicationTests {

	@Test
	public void testDefaultSettings() throws Exception {
		GitCloner gitCloner = new GitCloner("http://localhost:8090/git/test");
		gitCloner.cloneRepositoryToTempFolder(true);
	}

	@Test
	public void createRepo() throws Exception {
		
	}


}
