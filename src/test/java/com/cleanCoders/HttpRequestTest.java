package com.cleanCoders;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

public class HttpRequestTest {
    HttpRequest httpRequest;
    ByteArrayInputStream bais;
    String body = """
            --BOUNDARY\r
            Content-Disposition: form-data; name="hello"\r
            Content-Type: text/html\r
            \r
            <h1>hello</h1>\r
            --BOUNDARY--\r
            """;
    String request = """
            GET /hello HTTP/1.1\r
            Content-Type: text/html\r
            Server: httpServer1.1\r
            Content-Type: multipart/form-data; boundary=BOUNDARY\r
            Content-Length: %s\r
            \r
            %s""".formatted(body.length(), body);

    @BeforeEach
    void setup() throws IOException {
        bais = new ByteArrayInputStream(request.getBytes());
        httpRequest = new HttpRequest(bais);
    }

    @Test
    void parseRequestPutsHeaderInMap() {
        var header = """
                GET /hello HTTP/1.1\r
                Content-Type: text/html\r
                Server: httpServer1.1\r
                Content-Type: multipart/form-data; boundary=BOUNDARY\r
                Content-Length: %s\r
                \r
                """.formatted(body.length());
        assertEquals(header, httpRequest.get("header"));
    }

    @Test
    void parsesRequestStartLine() {
        var startLine = "GET /hello HTTP/1.1";
        assertEquals(startLine, httpRequest.get("startLine"));
    }

    @Test
    void parseRequestPutsTargetInMap() {
        var path = "/hello";
        assertEquals(path, httpRequest.get("path"));
    }

    @Test
    void parseRequestPutsMethodInMap() {
        var method = "GET";
        assertEquals(method, httpRequest.get("method"));
    }

    @Test
    void parseRequestPutsServerInMap() {
        var server = "httpServer1.1";
        assertEquals(server, httpRequest.get("Server"));
    }

    @Test
    void parseRequestPutsContentTypeInMap() {
        var server = "multipart/form-data; boundary=BOUNDARY";
        assertEquals(server, httpRequest.get("Content-Type"));
    }

    @Test
    void returnsNullIfNoBoundary() throws IOException {
        byte[] request = """
            GET /hello HTTP/1.1\r
            Content-Type: text/html\r
            Server: httpServer1.1\r
            Content-Length: 4\r
            \r
            text\r
            """.getBytes();
        ByteArrayInputStream bais = new ByteArrayInputStream(request);
        HttpRequest httpRequest = new HttpRequest(bais);
        assertNull(httpRequest.get("boundary"));
    }

    @Test
    void returnsBoundaryIfExists() throws IOException {

        ByteArrayInputStream bais = new ByteArrayInputStream(request.getBytes());
        HttpRequest request = new HttpRequest(bais);
        assertEquals("--BOUNDARY", request.get("boundary"));
    }

    @Test
    void returnsNullForNoBody() throws IOException {
        byte[] request = """
            GET /hello HTTP/1.1\r
            Content-Type: text/html\r
            Server: httpServer1.1\r
            \r
            """.getBytes();
        ByteArrayInputStream bais = new ByteArrayInputStream(request);
        assertNull(new HttpRequest(bais).getBody());
    }

    @Test
    void returnsBodyIfExists() throws IOException {
        ByteArrayInputStream bais = new ByteArrayInputStream(request.getBytes());
        byte[] result = new HttpRequest(bais).getBody();

        assertArrayEquals(body.getBytes(), result);
    }
}