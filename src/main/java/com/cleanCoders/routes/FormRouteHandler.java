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
        String fileName = getFileName(request);
        String fullRequest = request.get("request");
        System.out.println("request length: " + fullRequest.length());
        System.out.println("body length: " + request.get("body").length());

        return "<h2>POST Form</h2>"
                + "<li>file name: " + fileName + "</li>"
                + "<p>" + fullRequest + "</p>";
    }

    private String getFileName(HttpRequest request) {
        String body = request.get("body");
        if (body != null) {
            String contentDisposition = body.split("\r\n")[1].replace("Content-Disposition: form-data;", "");
            return contentDisposition.split(";")[1].split("=")[1].replace("\"", "");
        }

        return "";
    }
}
