package com.cleanCoders.routes;

import com.cleanCoders.HttpRequest;
import com.cleanCoders.QueryParser;
import com.cleanCoders.ResponseBuilder;
import com.cleanCoders.RouteHandler;

import java.io.IOException;
import java.util.Objects;

public class FormRouteHandler implements RouteHandler {

    @Override
    public byte[] handle(HttpRequest request) throws IOException {
        String response = handleGetRequest(request);
        ResponseBuilder rb = new ResponseBuilder();

        if (Objects.equals("POST", request.get("method")))
            response = handlePostRequest(request);

        return rb.buildResponse(response.getBytes());
    }

    public String handleGetRequest(HttpRequest request) {
        String filePath = request.get("path");
        var queryMap = new QueryParser().parse(filePath);
        var listItems = new StringBuilder();

        for (String key : queryMap.keySet()) {
            var value = queryMap.get(key);
            listItems
                    .append("<li>")
                    .append(key)
                    .append(": ")
                    .append(value)
                    .append("</li>\n");
        }
        return  "<h2>GET Form</h2>\n" + listItems;
    }

    public String handlePostRequest(HttpRequest request) {
        String fileName = request.get("file name");
        String contentType = request.get("content type");
        String body = request.get("body");
        String boundary = request.get("boundary");
        String bodyHeader = request.get("body header");
        String CLRF = "\r\n";
        int fileSize = body.length() - boundary.length() - bodyHeader.length() - (CLRF.length() * 6);

        return "<h2>POST Form</h2>"
                + "<li>file name: " + fileName + "</li>"
                + "<li>content type: " + contentType + "</li>"
                + "<li>file size: " + fileSize + "</li>";

    }
}