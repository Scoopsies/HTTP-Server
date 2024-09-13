package com.cleanCoders.responses;

import com.cleanCoders.FileContent;
import com.cleanCoders.ResponseBuilder;

import java.io.IOException;


public class FileResponse {
    public static byte[] build (String filePath) throws IOException {
        ResponseBuilder responseBuilder = new ResponseBuilder();
        FileContent fc = new FileContent();
        byte[] fileContent = fc.getFileContent(filePath);
        String contentType = fc.getContentType(filePath);

        return responseBuilder.buildResponse(contentType, fileContent);
    }
}
