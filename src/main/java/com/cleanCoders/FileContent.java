package com.cleanCoders;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;

public class FileContent {

    public byte[] getFileContent(File file) throws IOException {
        String extension = getExtensionOf(file.getName());
        if (isTextFile(extension))
            return getTextFileContent(file).getBytes();
        return getBinaryFileContent(file);
    }

    public byte[] getFileContent(String filePath) throws IOException {
        File file = new File(filePath);
        return getFileContent(file);
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

    public String getResourceTextFileContent(String path) throws IOException {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();

        try (InputStream inputStream = classLoader.getResourceAsStream(path)) {
            assert inputStream != null;
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {

                StringBuilder content = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    content.append(line).append("\n");
                }

                return content.toString();
            }
        }
    }

    public String getContentType(String pathString) {
        var path = Path.of(pathString);
        String contentType;

        try {
            contentType = Files.probeContentType(path);
            if (contentType == null)
                contentType = "text/html";
        } catch (IOException ioe) {
            contentType = "text/html";
            System.out.println(ioe.getMessage());
        }
        return "Content-Type: " + contentType + "\r\n";
    }
}