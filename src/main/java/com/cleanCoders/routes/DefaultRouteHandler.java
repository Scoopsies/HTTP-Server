package com.cleanCoders.routes;

import com.cleanCoders.*;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class DefaultRouteHandler implements RouteHandler {
    String root;

    public  DefaultRouteHandler(String root) {
        this.root = root;
    }

    @Override
    public byte[] handle(HttpRequest request) throws IOException {
        return getResponse(request, this.root);
    }

    public byte[] getResponse(HttpRequest request, String root) throws IOException {
        String filePath = request.get("path");
        File indexHTML = new File(root + filePath + "/index.html");
        File file = new File(root + filePath);
        ResponseBuilder responseBuilder = new ResponseBuilder();

        if (indexHTML.exists()) {
            byte[] fileContent = new FileContent().getFileContent(indexHTML);
            return responseBuilder.buildResponse(fileContent);
        }

        if (file.isFile()) {
            byte[] fileContent = new FileContent().getFileContent(file);
            return responseBuilder.buildResponse(getContentType(filePath), fileContent);
        }

        if (file.isDirectory()) {
            String directoryListing = new DirectoryBuilder().build(file, root);
            return responseBuilder.buildResponse(directoryListing.getBytes());
        }

        String fileContent = new FileContent().getResourceTextFileContent("404/index.html");
        return responseBuilder.buildResponse("404 Not Found", getContentType(filePath), fileContent.getBytes());
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
        return "Content-Type: " + contentType + "\n";
    }
}