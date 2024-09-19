package com.cleanCoders.routes;

import com.cleanCoders.HttpRequest;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class FormRouteHandlerTest {

    @Test
    void fizzBuzz1and2RespondsAppropriately() throws IOException {
        FormRouteHandler formRoute = new FormRouteHandler();
        HttpRequest request = new HttpRequest(new ByteArrayInputStream("GET /form?fizz=1&buzz=2 HTTP/1.1\r\n\r\n".getBytes()));

        byte[] result = formRoute.handle(request);

        var expected = """
            HTTP/1.1 200 OK\r
            Content-Type: text/html\r
            Server: httpServer\r
            \r
            <h2>GET Form</h2>
            <li>fizz: 1</li>
            <li>buzz: 2</li>
            """;

        assertEquals(expected, new String(result));
    }

    @Test
    void fizzBuzz3and4RespondsAppropriately() throws IOException {
        FormRouteHandler formRoute = new FormRouteHandler();
        HttpRequest request = new HttpRequest(new ByteArrayInputStream("GET /form?fizz=3&buzz=4 HTTP/1.1\r\n\r\n".getBytes()));

        byte[] result = formRoute.handle(request);

        var expected = """
            HTTP/1.1 200 OK\r
            Content-Type: text/html\r
            Server: httpServer\r
            \r
            <h2>GET Form</h2>
            <li>fizz: 3</li>
            <li>buzz: 4</li>
            """;

        assertEquals(expected, new String(result));
    }

    @Test
    void additionalQueryAlsoShowUp() throws IOException {
        FormRouteHandler formRoute = new FormRouteHandler();
        HttpRequest request = new HttpRequest(new ByteArrayInputStream("GET /form?fizz=3&buzz=5&fizzBuzz=15 HTTP/1.1\r\n\r\n".getBytes()));

        byte[] result = formRoute.handle(request);

        var expected = """
            HTTP/1.1 200 OK\r
            Content-Type: text/html\r
            Server: httpServer\r
            \r
            <h2>GET Form</h2>
            <li>fizzBuzz: 15</li>
            <li>fizz: 3</li>
            <li>buzz: 5</li>
            """;

        assertEquals(expected, new String(result));
    }

//    @Test
//    void formPostContainsAFormPostHeader() throws IOException {
//        FormRouteHandler formRouteHandler = new FormRouteHandler();
//        String body = """
//                ------WebKitFormBoundarynAd0dgsCmrEVtjpz\r
//                Content-Disposition: form-data; name="file"; filename="autobot.jpg"\r
//                \r
//                body
//                """;
//
//        System.out.println("body: " + body);
//        String requestString = """
//                POST /form HTTP/1.1\r
//                Content-Type: multipart/form-data\r
//                Content-Length: %s\r
//                \r
//                %s
//                """.formatted(body.length(), body);
//        HttpRequest request = new HttpRequest(new ByteArrayInputStream(requestString.getBytes()));
//        String result = formRouteHandler.handlePostRequest(request);
//        System.out.println(result);
//        assertTrue(result.contains("<h2>POST Form</h2>"));
//    }
//
//    @Test
//    void formPostContainsAContentType() throws IOException {
//        FormRouteHandler formRouteHandler = new FormRouteHandler();
//        String body = """
//                ------WebKitFormBoundarynAd0dgsCmrEVtjpz\r
//                Content-Disposition: form-data; name="file"; filename="autobot.jpg"\r
//                Content-Type: application/octet-stream\r
//                \r
//                body
//                """;
//
//        System.out.println("body: " + body);
//        String requestString = """
//                POST /form HTTP/1.1\r
//                Content-Type: multipart/form-data\r
//                Content-Length: %s\r
//                \r
//                %s
//                """.formatted(body.length(), body);
//        HttpRequest request = new HttpRequest(new ByteArrayInputStream(requestString.getBytes()));
//        String result = formRouteHandler.handlePostRequest(request);
//        System.out.println(result);
//        assertTrue(result.contains("<li>content type: application/octet-stream</li>"));
//    }
//
//    @Test
//    void formPostContainsAFileName() throws IOException {
//        FormRouteHandler formRouteHandler = new FormRouteHandler();
//        String body = """
//                ------WebKitFormBoundarynAd0dgsCmrEVtjpz\r
//                Content-Disposition: form-data; name="file"; filename="autobot.jpg"\r
//                \r
//                body
//                """;
//
//
//        String requestString = """
//                POST /form HTTP/1.1\r
//                Content-Type: multipart/form-data\r
//                Content-Length: %s\r
//                \r
//                %s
//                """.formatted(body.length(), body);
//        HttpRequest request = new HttpRequest(new ByteArrayInputStream(requestString.getBytes()));
//        String result = formRouteHandler.handlePostRequest(request);
//        System.out.println(result);
//        assertTrue(result.contains("<li>file name: autobot.jpg</li>"));
//    }
//
//    @Test
//    void formPostContainsAFileSize() throws IOException {
//        FormRouteHandler formRouteHandler = new FormRouteHandler();
//        String body = """
//                --BOUNDARY\r
//                Content-Disposition: form-data; name="file"; filename="autobot.jpg"\r
//                \r
//                body\r
//                BOUNDARY--\r""";
//
//
//        String requestString = """
//                POST /form HTTP/1.1\r
//                Content-Type: multipart/form-data; boundary=BOUNDARY\r
//                Content-Length: %s\r
//                \r
//                %s
//                """.formatted(body.length(), body);
//        HttpRequest request = new HttpRequest(new ByteArrayInputStream(requestString.getBytes()));
//        String result = formRouteHandler.handlePostRequest(request);
//        System.out.println(result);
//        assertTrue(result.contains("<li>file size: 4</li>"));
//    }

}