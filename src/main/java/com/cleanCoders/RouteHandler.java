package com.cleanCoders;

import java.io.IOException;

public interface RouteHandler {
    byte[] handle(HttpRequest request) throws IOException;
}
