package com.cleanCoders;

import com.cleanCoders.routes.DefaultRouteHandler;

import java.io.IOException;
import java.util.HashMap;

public class Router {
    protected final HashMap<String, RouteHandler> routes = new HashMap<>();
    private RouteHandler defaultHandler = new DefaultRouteHandler(".");

    public void addRoute(String path, RouteHandler handler) {
        routes.put(path, handler);
    }

    public byte[] route(HttpRequest request) throws IOException {
        String path = getPathRoot(request.get("path"));
        RouteHandler handler = routes.getOrDefault(path, defaultHandler);
        return handler.handle(request);
    }

    String getPathRoot(String path) {
        String[] parts = path.split("/");
        if (parts.length > 2)
            return "/" + parts[1];

        parts = path.split("\\?");
        if (parts.length > 1)
            return parts[0];

        return path;
    }

    public void setDefaultRoot(String root) {
        this.defaultHandler = new DefaultRouteHandler(root);
    }
}