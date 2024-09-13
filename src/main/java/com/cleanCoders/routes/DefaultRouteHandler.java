package com.cleanCoders.routes;

import com.cleanCoders.*;
import com.cleanCoders.responses.DirectoryListingResponse;
import com.cleanCoders.responses.FileNotFound404;
import com.cleanCoders.responses.FileResponse;

import java.io.File;
import java.io.IOException;

public class DefaultRouteHandler implements RouteHandler {
    String root;

    public  DefaultRouteHandler(String root) {
        this.root = root;
    }

    @Override
    public byte[] handle(HttpRequest request) throws IOException {
        return getResponse(request);
    }

    public byte[] getResponse(HttpRequest request) throws IOException {
        String filePath = this.root + request.get("path");
        File file = new File(filePath);

        if (hasIndexHtml(file)) {
            return FileResponse.build(filePath + "/index.html");
        }

        if (file.isFile())
            return FileResponse.build(filePath);


        if (file.isDirectory()) {
            return DirectoryListingResponse.build(filePath, this.root);
        }

        return FileNotFound404.build(filePath);
    }

    private boolean hasIndexHtml(File file) {
        File indexHTML = new File(file.getPath() + "/index.html");
        return indexHTML.exists();
    }

}