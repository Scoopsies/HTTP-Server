package com.cleanCoders;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class ResponseBuilder {
    final String CLRF = "\r\n";

    public byte[] buildResponse(String status, String contentType, byte[] content) throws IOException {
        ByteArrayOutputStream responseStream = new ByteArrayOutputStream();
        responseStream.write(buildResponseStatus(status).getBytes());
        responseStream.write(contentType.getBytes());
        responseStream.write("Server: httpServer".getBytes());
        responseStream.write(CLRF.getBytes());
        responseStream.write(CLRF.getBytes());
        responseStream.write(content);
        return responseStream.toByteArray();
    }

    public byte[] buildResponse(String contentType, byte[] content) throws IOException {
        return buildResponse("200 OK", contentType, content);
    }

    public byte[] buildResponse(byte[] content) throws IOException {
        return buildResponse("Content-Type: text/html\n", content);
    }

    public String buildResponseStatus(String status) {
        return "HTTP/1.1 " + status + CLRF;
    }
}