package com.cleanCoders.routes;

import com.cleanCoders.HttpRequest;
import com.cleanCoders.QueryParser;
import com.cleanCoders.ResponseBuilder;
import com.cleanCoders.RouteHandler;
import com.cleanCoders.multipart.Body;
import com.cleanCoders.multipart.Part;

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

    public static String handleGetRequest(HttpRequest request) {
        String filePath = request.get("path");
        var queryMap = QueryParser.parse(filePath);
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
        byte[] bodyBytes = request.getBody();
        byte[] boundary = request.get("boundary").getBytes();
        Body body = new Body(bodyBytes, boundary);
        Part part = body.getPart("file");

        return "<h2>POST Form</h2>"
                + "<li>content type: " + part.getContentType() + "</li>"
                + "<li>file name: " + part.getFileName() + "</li>"
                + "<li>file size: " + part.getContent().length + "</li>";
    }
}