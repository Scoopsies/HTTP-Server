package com.cleanCoders.routes;

import com.cleanCoders.HttpRequest;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ListingRouteHandlerTest {
    @Test
    void listingRouteBuildsADirectoryForTestRoot() throws IOException {
        ListingRouteHandler lr = new ListingRouteHandler(".");
        HttpRequest request = new HttpRequest(new ByteArrayInputStream("GET /listing/testRoot/hello HTTP/1.1\r\n".getBytes()));

        byte[] result = lr.handle(request);

        String body = "<h1>Directory Listing for ./testRoot/hello</h1>\r\n"
                + "<ul>"
                + "<li><a href=\"/testRoot/hello/index.html\">index.html</a></li>"
                + "<li><a href=\"/testRoot/hello/miata.gif\">miata.gif</a></li>"
                + "</ul>";

        String expected = """
                HTTP/1.1 200 OK\r
                Content-Type: text/html\r
                Server: httpServer\r
                \r
                %s""".formatted(body);

        assertEquals(expected, new String(result));
    }
}