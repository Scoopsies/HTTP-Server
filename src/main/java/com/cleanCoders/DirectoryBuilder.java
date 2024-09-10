package com.cleanCoders;

import java.io.File;
import java.io.IOException;

public class DirectoryBuilder {

    public String build(File directory, String root) throws IOException {
        var listing = directory.list();
        var rootPath = new File(root).getCanonicalPath();
        var path = directory.getCanonicalPath().replace(rootPath, ".");
        var stringBuilder = new StringBuilder();

        stringBuilder.append("<h1>Directory Listing for ");
        stringBuilder.append(path);
        stringBuilder.append("</h1>\n<ul>");

        assert listing != null;
        for (String fileName : listing) {
            var currentFile = new File(directory.getPath() + "/" + fileName);
            var currentFilePath = currentFile.getCanonicalPath().replace(rootPath, "");

            stringBuilder.append("<li><a href=\"");
            if (currentFile.isDirectory())
                stringBuilder.append("/listing");
            stringBuilder.append(currentFilePath);
            stringBuilder.append("\">");
            stringBuilder.append(fileName);
            stringBuilder.append("</a></li>");
        }
        stringBuilder.append("</ul>");

        return stringBuilder.toString();
    }

    public String build(String path, String root) throws IOException {
        File file = new File(path);
        return build(file, root);
    }
}
