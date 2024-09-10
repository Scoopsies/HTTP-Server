package com.cleanCoders;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class HttpRequestTest {
    HttpRequest httpRequest;
    ByteArrayInputStream bais;
    String request = """
                GET /hello HTTP/1.1\r
                Content-Type: text/html\r
                Server: httpServer1.1\r
                Content-Type: image/jpg\r
                \r
                <h2>GET Form</h2>
                <li>foo: 1</li>
                <li>bar: 2</li>
                """;

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
                Content-Type: image/jpg""";
        assertEquals(header, httpRequest.get("header"));
    }

    @Test
    void parseRequestPutsStartLineInMap() {
        var startLine = "GET /hello HTTP/1.1";
        assertEquals(startLine, httpRequest.get("startLine"));
    }

    @Test
    void parseRequestPutsBodyInMap() {
        var body = """
                <h2>GET Form</h2>
                <li>foo: 1</li>
                <li>bar: 2</li>
                """;

        assertEquals(body, httpRequest.get("body"));
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
        var server = "image/jpg";
        assertEquals(server, httpRequest.get("Content-Type"));
    }
}