package com.cleanCoders;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class ResponseBuilder {
    final String CLRF = "\r\n";

    public byte[] buildResponse(ByteArrayOutputStream responseStream,
                                 String status,
                                 String contentType, byte[] content) throws IOException {
        responseStream.write(buildResponseStatus(status).getBytes());
        responseStream.write(contentType.getBytes());
        responseStream.write("Server: httpServer".getBytes());
        responseStream.write(CLRF.getBytes());
        responseStream.write(CLRF.getBytes());
        responseStream.write(content);
        return responseStream.toByteArray();
    }

    public byte[] buildResponse(ByteArrayOutputStream responseStream, String contentType, byte[] content) throws IOException {
        return buildResponse(responseStream, "200 OK", contentType, content);
    }

    public byte[] buildResponse(ByteArrayOutputStream responseStream, byte[] content) throws IOException {
        return buildResponse(responseStream, "200 OK", "Content-Type: text/html\n", content);
    }

    public byte[] buildResponse(byte[] content) throws IOException {
        ByteArrayOutputStream responseStream = new ByteArrayOutputStream();
        return buildResponse(responseStream, "Content-Type: text/html\n", content);
    }

    public String buildResponseStatus(String status) {
        return "HTTP/1.1 " + status + CLRF;
    }
}