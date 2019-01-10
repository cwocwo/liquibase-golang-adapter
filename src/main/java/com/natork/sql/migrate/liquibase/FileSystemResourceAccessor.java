/**
 * 
 */
package com.natork.sql.migrate.liquibase;

import java.io.File;
import java.util.Set;

/**
 * @author caiweiwei
 *
 */
public class FileSystemResourceAccessor extends liquibase.resource.FileSystemResourceAccessor {
    
    
    
    public FileSystemResourceAccessor() {
        super();
    }

    public FileSystemResourceAccessor(String base) {
        super(base);
    }

    @Override
    protected void getContents(File rootFile, boolean recursive, boolean includeFiles, boolean includeDirectories, String basePath, Set<String> returnSet) {
        File[] files = rootFile.listFiles();
        if (files == null) {
            return;
        }
        for (File file : files) {
            if (file.isDirectory()) {
                if (includeDirectories) {
                    returnSet.add(file.getAbsolutePath());
                }
                if (recursive) {
                    getContents(file, recursive, includeFiles, includeDirectories, basePath, returnSet);
                }
            } else {
                if (includeFiles) {
                    returnSet.add(file.getAbsolutePath());
                }
            }
        }
    }
}
