package com.cleanCoders.routes;

import com.cleanCoders.DirectoryBuilder;
import com.cleanCoders.HttpRequest;
import com.cleanCoders.ResponseBuilder;
import com.cleanCoders.RouteHandler;
import com.cleanCoders.responses.FileNotFound404;

import java.io.File;
import java.io.IOException;


public class ListingRouteHandler implements RouteHandler {
    String root;

    public ListingRouteHandler(String root){
        this.root = root;
    }

    @Override
    public byte[] handle(HttpRequest request) throws IOException {
        String filePath = request.get("path").replace("/listing", this.root);

        if (!isDirectory(filePath))
            return FileNotFound404.build(request.get("path"));

        return handleValidRequest(request);
    }

    private boolean isDirectory(String filePath) {
        File file = new File(filePath);
        return file.isDirectory();
    }

    private byte[] handleValidRequest(HttpRequest request) throws IOException {
        String filePath = request.get("path").replace("/listing", root);
        ResponseBuilder rb = new ResponseBuilder();
        DirectoryBuilder db = new DirectoryBuilder();

        return rb.buildResponse(db.build(filePath, root).getBytes());
    }
}