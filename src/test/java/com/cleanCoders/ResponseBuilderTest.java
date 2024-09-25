package com.cleanCoders;

import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ResponseBuilderTest {
    @Test
    void buildResponseWithCustomStatusAndContentType() throws IOException {
        ResponseBuilder responseBuilder = new ResponseBuilder();
        String status = "404 Not Found";
        String contentType = "Content-Type: text/plain";
        byte[] content = "File not found".getBytes();
        byte[] response = responseBuilder.buildResponse(status, contentType, content);

        String expectedResponse = """
            HTTP/1.1 404 Not Found\r
            Content-Type: text/plain\r
            Server: httpServer\r
            \r
            File not found""";

        assertEquals(expectedResponse, new String(response));
    }

    @Test
    void buildsResponseWithDefaultHeader() throws IOException {
        ResponseBuilder responseBuilder = new ResponseBuilder();
        byte[] content = "text".getBytes();
        byte[] response = responseBuilder.buildResponse(content);

        String expectedResponse = """
            HTTP/1.1 200 OK\r
            Content-Type: text/html\r
            Server: httpServer\r
            \r
            text""";

        assertEquals(expectedResponse, new String(response));
    }

    @Test
    void buildResponseWithDefaultStatusAndCustomContentType() throws IOException {
        ResponseBuilder responseBuilder = new ResponseBuilder();
        String contentType = "Content-Type: application/json";
        byte[] content = "{\"key\": \"value\"}".getBytes();

        byte[] response = responseBuilder.buildResponse(contentType, content);

        String expectedResponse = """
            HTTP/1.1 200 OK\r
            Content-Type: application/json\r
            Server: httpServer\r
            \r
            {"key": "value"}""";

        assertEquals(expectedResponse, new String(response));
    }

    @Test
    void buildResponseStatusReturnsCorrectStatusLine() {
        ResponseBuilder responseBuilder = new ResponseBuilder();

        String status = "404 Not Found";
        String result = responseBuilder.buildResponseStatus(status);

        String expected = "HTTP/1.1 404 Not Found\r\n";
        assertEquals(expected, result, "The status line should be correctly formatted as 'HTTP/1.1 404 Not Found'");
    }
}