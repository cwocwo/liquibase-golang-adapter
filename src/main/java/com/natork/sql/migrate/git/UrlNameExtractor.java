package com.natork.sql.migrate.git;

public class UrlNameExtractor {

    public static String extractName(final String url) {
        return url.substring(url.lastIndexOf('/') + 1).replace(".git", "");
    }

}
