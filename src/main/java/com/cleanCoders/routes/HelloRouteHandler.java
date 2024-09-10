package com.cleanCoders.routes;

import com.cleanCoders.HttpRequest;
import com.cleanCoders.RouteHandler;
import java.io.IOException;

public class HelloRouteHandler implements RouteHandler {
    @Override
    public byte[] handle(HttpRequest request) throws IOException {
        return """
            HTTP/1.1 200 OK\r
            Content-Type: text/html
            Server: httpServer\r
            \r
            <h1>Hello!</h1>
            """.getBytes();
    }
}
