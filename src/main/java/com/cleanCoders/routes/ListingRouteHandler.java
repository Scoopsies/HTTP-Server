package com.cleanCoders.routes;

import com.cleanCoders.DirectoryBuilder;
import com.cleanCoders.HttpRequest;
import com.cleanCoders.ResponseBuilder;
import com.cleanCoders.RouteHandler;

import java.io.IOException;


public class ListingRouteHandler implements RouteHandler {
    String root;

    public ListingRouteHandler(String root){
        this.root = root;
    }

    @Override
    public byte[] handle(HttpRequest request) throws IOException {
        String filePath = request.get("path").replace("/listing", root);
        ResponseBuilder rb = new ResponseBuilder();
        DirectoryBuilder db = new DirectoryBuilder();

        return rb.buildResponse(db.build(filePath, root).getBytes());
    }
}