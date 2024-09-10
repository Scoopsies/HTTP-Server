package com.cleanCoders.routes;

import com.cleanCoders.HttpRequest;
import com.cleanCoders.QueryParser;
import com.cleanCoders.ResponseBuilder;
import com.cleanCoders.RouteHandler;

import java.io.IOException;

public class FormRouteHandler implements RouteHandler {

    @Override
    public byte[] handle(HttpRequest request) throws IOException {
        String filePath = request.get("path");
        var queryMap = new QueryParser().parse(filePath);
        var listItems = new StringBuilder();
        ResponseBuilder rb = new ResponseBuilder();
        for (String key : queryMap.keySet()) {
            var value = queryMap.get(key);
            listItems
                    .append("<li>")
                    .append(key)
                    .append(": ")
                    .append(value)
                    .append("</li>\n");
        }
        var response = "<h2>GET Form</h2>\n" + listItems;
        return rb.buildResponse(response.getBytes());
    }
}
