package com.cleanCoders.routes;

import com.cleanCoders.HttpRequest;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class HelloRouteHandlerTest {
    @Test
    void helloRouteRespondsAsExpected() throws IOException {
        HelloRouteHandler helloRoute = new HelloRouteHandler();
        HttpRequest request = new HttpRequest(new ByteArrayInputStream("GET /hello HTTP/1.1\r\n\r\n".getBytes()));

        byte[] result = helloRoute.handle(request);

        var expected = """
                HTTP/1.1 200 OK\r
                Content-Type: text/html
                Server: httpServer\r
                \r
                <h1>Hello!</h1>
                """;


        assertEquals(expected, new String(result));
    }
}
