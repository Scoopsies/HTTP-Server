package com.cleanCoders.routes;

import com.cleanCoders.HttpRequest;
import com.cleanCoders.ResponseBuilder;
import com.cleanCoders.RouteHandler;

import java.io.IOException;
import java.util.HashMap;

public class FormRouteHandler implements RouteHandler {

    @Override
    public byte[] handle(HttpRequest request) throws IOException {
        String filePath = request.get("path");
        var queryMap = parseQuery(filePath);
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

    private HashMap<String, String> parseQuery(String filePath) {
        var queryMap = new HashMap<String, String>();
        var query = filePath.split("\\?");
        if (query.length > 1) {
            var queryArray = query[1].replace("=", " ").replace("&", " ").split("\\s");
            for (int i = 0; i < queryArray.length; i += 2) {
                queryMap.put(queryArray[i], queryArray[i + 1]);
            }
        }

        return queryMap;
    }
}
