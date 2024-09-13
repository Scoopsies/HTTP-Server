package com.cleanCoders.responses;

import com.cleanCoders.FileContent;
import com.cleanCoders.ResponseBuilder;

import java.io.IOException;

public class FileNotFound404 {
    public static byte[] build(String filePath) throws IOException {
        ResponseBuilder responseBuilder = new ResponseBuilder();
        FileContent fc = new FileContent();
        String fileContent = fc.getResourceTextFileContent("404/index.html");
        String contentType = fc.getContentType(filePath);

        return responseBuilder.buildResponse("404 Not Found", contentType, fileContent.getBytes());
    }
}
