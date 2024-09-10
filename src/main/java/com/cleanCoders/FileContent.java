package com.cleanCoders;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Objects;

public class FileContent {

    public byte[] getFileContent(File file) throws IOException {
        String extension = getExtensionOf(file.getName());
        if (isTextFile(extension))
            return getTextFileContent(file).getBytes();
        return getBinaryFileContent(file);
    }

    public String getExtensionOf(String path) {
        int lastDotIndex = path.lastIndexOf(".");
        return path.substring(lastDotIndex + 1);
    }

    boolean isTextFile(String extension) {
        return Objects.equals("html", extension) || Objects.equals("txt", extension);
    }

    public String getTextFileContent(File file) throws IOException {
        StringBuilder htmlContent = new StringBuilder();

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                htmlContent.append(line).append("\n");
            }
        }
        htmlContent.append("\n");

        if (!htmlContent.isEmpty()) {
            htmlContent.setLength(htmlContent.length() - 1);
        }

        return htmlContent.toString();
    }

    public String getTextFileContent(String filePath) throws IOException {
        File file = new File(filePath);
        return getTextFileContent(file);
    }

    public byte[] getBinaryFileContent(File file) throws IOException {
        var path = file.toPath();
        return Files.readAllBytes(path);
    }
}