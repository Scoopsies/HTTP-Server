package com.cleanCoders.routes;

import com.cleanCoders.DirectoryBuilder;
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
    void getResponseIfThereIsAnIndex() throws IOException {
        var inputStream = new ByteArrayInputStream("GET /hello HTTP/1.1".getBytes());
        var response = defaultRouteHandler.getResponse(new HttpRequest(inputStream));
        var expected = """
            HTTP/1.1 200 OK\r
            Content-Type: text/html\r
            Server: httpServer\r
            \r
            <h1>Hello!</h1>
            """;
        var result = new String(response);

        System.out.println(result.replace("\r\n", "CLRF"));
        System.out.println(expected.replace("\r\n", "CLRF"));
        assertEquals(expected, result);
    }

    @Test
    void getResponseIf404() throws IOException {
        var inputStream = new ByteArrayInputStream("GET /hamburger HTTP/1.1".getBytes());
        var response = defaultRouteHandler.getResponse(new HttpRequest(inputStream));
        var expected = """
            HTTP/1.1 404 Not Found\r
            Content-Type: text/html\r
            Server: httpServer\r
            \r
            <h1>404: This isn't the directory you are looking for.</h1>
            """;
        var result = new String(response);
        assertEquals(expected, result);
    }

    @Test
    void getDirectoryListingForThings() throws IOException {
        var directory = new File("./testRoot/things");
        String directoryListing = new DirectoryBuilder().build(directory, "./testRoot");
        var result =
                "<h1>Directory Listing for ./things</h1>\n"
                        + "<ul>"
                        + "<li><a href=\"/things/miata.pdf\">miata.pdf</a></li>"
                        + "<li><a href=\"/things/miata.jpg\">miata.jpg</a></li>"
                        + "<li><a href=\"/things/miata.png\">miata.png</a></li>"
                        + "<li><a href=\"/things/miata.txt\">miata.txt</a></li>"
                        + "<li><a href=\"/things/miata.gif\">miata.gif</a></li>"
                        + "</ul>";
        assertEquals(result, directoryListing);
    }

    @Test
    void getDirectoryListingFor404() throws IOException {
        var directory = new File("./testRoot/404");
        String directoryListing = new DirectoryBuilder().build(directory, "./testRoot");
        var result =
                "<h1>Directory Listing for ./404</h1>\n"
                        + "<ul>"
                        + "<li><a href=\"/404/index.html\">index.html</a></li>"
                        + "</ul>";
        assertEquals(result, directoryListing);
    }
}
