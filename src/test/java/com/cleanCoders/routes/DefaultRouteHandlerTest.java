package com.cleanCoders.routes;

import com.cleanCoders.HttpRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DefaultRouteHandlerTest {
    DefaultRouteHandler defaultRouteHandler;
    String root;

    @BeforeEach
    void setup() {
        root = "./testRoot";
        defaultRouteHandler = new DefaultRouteHandler(root);
    }

    @Test
    void getContentTypeReturnsHTML() {
        String contentType = defaultRouteHandler.getContentType("/hello/welcome.html");
        assertEquals("Content-Type: text/html\n", contentType);
    }

    @Test
    void getContentTypeReturnsPNG() {
        String contentType = defaultRouteHandler.getContentType("/things/miata.png");
        assertEquals("Content-Type: image/png\n", contentType);
    }

    @Test
    void getContentTypeReturnsGIF() {
        String contentType = defaultRouteHandler.getContentType("/things/miata.gif");
        assertEquals("Content-Type: image/gif\n", contentType);
    }

    @Test
    void getContentTypeReturnsJPG() {
        String contentType = defaultRouteHandler.getContentType("/things/miata.jpg");
        assertEquals("Content-Type: image/jpeg\n", contentType);
    }

    @Test
    void getContentTypeReturnsPDF() {
        String contentType = defaultRouteHandler.getContentType("/things/miata.pdf");
        assertEquals("Content-Type: application/pdf\n", contentType);
    }

    @Test
    void getResponseIfThereIsAnIndex() throws IOException {
        var inputStream = new ByteArrayInputStream("GET /hello HTTP/1.1".getBytes());
        var response = defaultRouteHandler.getResponse(new HttpRequest(inputStream), root);
        var expected = """
            HTTP/1.1 200 OK\r
            Content-Type: text/html
            Server: httpServer\r
            \r
            <h1>Hello!</h1>
            """;
        var result = new String(response);
        assertEquals(expected, result);
    }

    @Test
    void getResponseIf404() throws IOException {
        var inputStream = new ByteArrayInputStream("GET /hamburger HTTP/1.1".getBytes());
        var response = defaultRouteHandler.getResponse(new HttpRequest(inputStream), root);
        var expected = """
            HTTP/1.1 404 Not Found\r
            Content-Type: text/html
            Server: httpServer\r
            \r
            <h1>404: This isn't the directory you are looking for.</h1>
            """;
        var result = new String(response);
        assertEquals(expected, result);
    }

//    @Test
//    void getDirectoryListingForThings() throws IOException {
//        var directory = new File("./testRoot/things");
//        String directoryListing = new HttpResponse().buildDirectoryListing(directory, "./testRoot");
//        var result =
//                "<h1>Directory Listing for ./things</h1>\n"
//                        + "<ul>"
//                        + "<li><a href=\"/things/miata.pdf\">miata.pdf</a></li>"
//                        + "<li><a href=\"/things/miata.jpg\">miata.jpg</a></li>"
//                        + "<li><a href=\"/things/miata.png\">miata.png</a></li>"
//                        + "<li><a href=\"/things/miata.txt\">miata.txt</a></li>"
//                        + "<li><a href=\"/things/miata.gif\">miata.gif</a></li>"
//                        + "</ul>";
//        assertEquals(result, directoryListing);
//    }

//    @Test
//    void getDirectoryListingFor404() throws IOException {
//        var directory = new File("./testRoot/404");
//        String directoryListing = new HttpResponse().buildDirectoryListing(directory, "./testRoot");
//        var result =
//                "<h1>Directory Listing for ./404</h1>\n"
//                        + "<ul>"
//                        + "<li><a href=\"/404/index.html\">index.html</a></li>"
//                        + "</ul>";
//        assertEquals(result, directoryListing);
//    }
}
