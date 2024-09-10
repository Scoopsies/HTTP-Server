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
        System.out.println("Root: " + root);
        String filePath = request.get("path");
        File indexHTML = new File(root + filePath + "/index.html");
        File file = new File(root + filePath);
        ByteArrayOutputStream responseStream = new ByteArrayOutputStream();
        ResponseBuilder responseBuilder = new ResponseBuilder();

        if (indexHTML.exists()) {
            byte[] fileContent = new FileContent().getFileContent(indexHTML);
            return responseBuilder.buildResponse(responseStream, fileContent);
        }

        if (file.isFile()) {
            byte[] fileContent = new FileContent().getFileContent(file);
            return responseBuilder.buildResponse(responseStream, getContentType(filePath), fileContent);
        }

        if (file.isDirectory()) {
            String directoryListing = new DirectoryBuilder().build(file, root);
            return responseBuilder.buildResponse(responseStream, directoryListing.getBytes());
        }

        var fileNotFound = new File("/Users/scoops/Projects/httpServer1.1/404/index.html");
        byte[] fileContent = new FileContent().getFileContent(fileNotFound);
        return responseBuilder.buildResponse(responseStream, "404 Not Found", getContentType(filePath), fileContent);
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