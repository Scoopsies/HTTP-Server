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
        String body = request.get("body");
        String boundary = request.get("Content-Type").replace("multipart/form-data; boundary=", "");
        String fullHeader = request.get("header");
        String fullRequest = request.get("request");
        String contentDisposition = body.split("\r\n")[1];
        String contentType = request.get("body").split("\r\n")[2].replace("Content-Type: ", "");
        String content = request.get("body").split("\r\n")[4];
        int contentLength = Integer.parseInt(request.get("Content-Length"));
        String header = request.get("body").split("\r\n\r\n")[0];
        String file = request.get("body").split("\r\n\r\n")[1];
        String CLRF = "\r\n";

        return "<h2>POST Form</h2>"
                + "<li>file name: " + fileName + "</li>"
                + "<li>content type: " + contentType + "</li>"
                + "<li>file size: " + (body.length() - boundary.length() - header.length() - (CLRF.length() * 6)) + "</li>"
                + "<li>Content-Length: " + contentLength + "</li>"
                + "<li>Boundary: " + boundary + "</li>"
                + "<li>Boundary-length: " + boundary.length() + "</li>"
                + "<li>Header: " + header + "</li>"
                + "<li>Header-length: " + header.length() + "</li>"
                + "<li>body-length: " + body.length() + "</li>"
                ;

    }
}