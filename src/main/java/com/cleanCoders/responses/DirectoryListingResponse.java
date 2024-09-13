package com.cleanCoders.responses;

import com.cleanCoders.DirectoryBuilder;
import com.cleanCoders.ResponseBuilder;

import java.io.File;
import java.io.IOException;

public class DirectoryListingResponse {

    public static byte[] build(String filePath, String root) throws IOException {
        ResponseBuilder responseBuilder = new ResponseBuilder();
        File file = new File(filePath);
        String directoryListing = new DirectoryBuilder().build(file, root);
        return responseBuilder.buildResponse(directoryListing.getBytes());
    }
}
