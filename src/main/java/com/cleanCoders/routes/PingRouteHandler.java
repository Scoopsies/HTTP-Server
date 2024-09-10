package com.cleanCoders.routes;

import com.cleanCoders.*;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class PingRouteHandler implements RouteHandler {

    @Override
    public byte[] handle(HttpRequest request) throws IOException {
        String filePath = request.get("path");
        String template = new FileContent().getTextFileContent("/Users/scoops/Projects/HttpServer/testRoot/ping/index.html");
        String sleepModifier = filePath.replace("/ping/", "");
        ResponseBuilder rb = new ResponseBuilder();
        int timeToSleep;

        try {
            timeToSleep = Integer.parseInt(sleepModifier) * 1000;
        } catch (Exception e) {
            timeToSleep = 0;
        }

        String html = template.formatted(getCurrentTime(), "%s");

        try {
            Thread.sleep(timeToSleep);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        html = html.formatted(getCurrentTime());

        return rb.buildResponse(html.getBytes());
    }

    public String getCurrentTime() {
        var time = LocalDateTime.now();
        var formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return time.format(formatter);
    }
}
