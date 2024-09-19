package com.cleanCoders.routes;

import com.cleanCoders.HttpRequest;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ListingRouteHandlerTest {
    String expectedTemplate = """
                HTTP/1.1 200 OK\r
                Content-Type: text/html\r
                Server: httpServer\r
                \r
                %s""";

    @Test
    void listingRouteBuildsADirectoryForTestRootHello() throws IOException {
        ListingRouteHandler lr = new ListingRouteHandler(".");
        HttpRequest request = new HttpRequest(new ByteArrayInputStream("GET /listing/testRoot/hello HTTP/1.1\r\n\r\n".getBytes()));
        byte[] result = lr.handle(request);
        String body = "<h1>Directory Listing for ./testRoot/hello</h1>\n"
                + "<ul>"
                + "<li><a href=\"/testRoot/hello/index.html\">index.html</a></li>"
                + "<li><a href=\"/testRoot/hello/miata.gif\">miata.gif</a></li>"
                + "</ul>";

        String expected = expectedTemplate.formatted(body);
        String actual = new String(result);

        assertEquals(expected, actual);
    }

    @Test
    void listingRouteBuildsADirectoryForHelloWithTestRootAsRoot() throws IOException {
        ListingRouteHandler lr = new ListingRouteHandler("./testRoot");
        HttpRequest request = new HttpRequest(new ByteArrayInputStream("GET /listing/hello HTTP/1.1\r\n\r\n".getBytes()));
        byte[] result = lr.handle(request);
        String body = "<h1>Directory Listing for ./hello</h1>\n"
                + "<ul>"
                + "<li><a href=\"/hello/index.html\">index.html</a></li>"
                + "<li><a href=\"/hello/miata.gif\">miata.gif</a></li>"
                + "</ul>";

        String expected = expectedTemplate.formatted(body);
        String actual = new String(result);

        assertEquals(expected, actual);
    }

    @Test
    void listingRouteReturns404IfDirectoryDoesNotExist() throws IOException {
        ListingRouteHandler lr = new ListingRouteHandler(".");
        HttpRequest request = new HttpRequest(new ByteArrayInputStream("GET /listing/hamburger HTTP/1.1\r\n\r\n".getBytes()));
        byte[] result = lr.handle(request);

        String expected = """
            HTTP/1.1 404 Not Found\r
            Content-Type: text/html\r
            Server: httpServer\r
            \r
            <h1>404: This isn't the directory you are looking for.</h1>
            """;
        String actual = new String(result);

        assertEquals(expected, actual);
    }
}